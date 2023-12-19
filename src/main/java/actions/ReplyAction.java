package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.ReplyView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EmployeeService;
import services.ReplyService;
/**
 * 日報に関する処理を行うActionクラス
 *
 */
public class ReplyAction extends ActionBase {

    private ReplyService service;
    private EmployeeService empservice;
    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReplyService();
        empservice = new EmployeeService();

        //メソッドを実行
        invoke();
        service.close();
        empservice.close();

    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int replypage = getPage();
        List<ReplyView> replys = service.getAllPerPage(replypage);

        //全日報データの件数を取得
        long replysCount = service.countAll();

        putRequestScope(AttributeConst.REPLYS, replys); //取得した日報データ
        putRequestScope(AttributeConst.REPLYS_COUNT, replysCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, replypage); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_REPL_INDEX);
    }
    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //日報情報の空インスタンスに、日報の日付＝今日の日付を設定する
        ReplyView rlv = new ReplyView();
        rlv.setReply_date(LocalDateTime.now());
        putRequestScope(AttributeConst.REPLY, rlv); //日付のみ設定済みの日報インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_REPL_NEW);

    }
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDateTime day = null;
            if (getRequestParam(AttributeConst.REPLY_DATE) == null
                    || getRequestParam(AttributeConst.REPLY_DATE).equals("")) {
                day = LocalDateTime.now();
            } else {
                day = LocalDateTime.parse(getRequestParam(AttributeConst.REPLY_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);


            //パラメータの値をもとに日報情報のインスタンスを作成する
            ReplyView rlv = new ReplyView(
                    null,
                    ev, //ログインしている従業員を、日報作成者として登録する
                    day,
                    empservice.findOne(toNumber(getRequestParam(AttributeConst.MITA_ID))),
                    getRequestParam(AttributeConst.REPLY_CONTENT),
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(rlv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rlv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_REPL_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REPL, ForwardConst.CMD_INDEX);
            }
        }
    }
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReplyView rlv = service.findOne(toNumber(getRequestParam(AttributeConst.REPLY_ID)));

        if (rlv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rlv); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_REP_SHOW);
        }
    }
    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReplyView rlv = service.findOne(toNumber(getRequestParam(AttributeConst.REPLY_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (rlv == null || ev.getId() != rlv.getEmployee().getId()) {
            //該当の日報データが存在しない、または
            //ログインしている従業員が日報の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, rlv); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_REPL_EDIT);
        }

    }
    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に日報データを取得する
            ReplyView rlv = service.findOne(toNumber(getRequestParam(AttributeConst.REPLY_ID)));

            //入力された日報内容を設定する
            rlv.setReply_date(toLocalDateTime(getRequestParam(AttributeConst.REPLY_DATE)));
            rlv.setContent(getRequestParam(AttributeConst.REPLY_CONTENT));

            //日報データを更新する
            List<String> errors = service.update(rlv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rlv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REPL, ForwardConst.CMD_INDEX);

            }
        }
    }
}