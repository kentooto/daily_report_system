package actions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TelView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EmployeeService;
import services.TelService;

/**
 * 電話メモに関する処理を行うActionクラス
 *
 */
public class TelAction extends ActionBase {

    private TelService telService;
    private EmployeeService empservice;
    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        telService = new TelService();
        empservice = new EmployeeService();
        //メソッドを実行
        invoke();
        telService.close();
        empservice.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する電話メモデータを取得
        int telpage = getPage();
        List<TelView> tel_memo = telService.getAllPerPage(telpage);

        //全電話メモデータの件数を取得
        long tels_count = telService.countAll();

        putRequestScope(AttributeConst.TELS, tel_memo); //取得した日報データ
        putRequestScope(AttributeConst.TEL_COUNT, tels_count); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, telpage); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TEL_INDEX);
    }
    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //電話メモ情報の空インスタンスに、電話メモの日付＝今日の日付を設定する
        TelView tv = new TelView();
        tv.setTel_memo_date(LocalDateTime.now());
        putRequestScope(AttributeConst.TEL, tv); //日付のみ設定済みの電話メモインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_TEL_NEW);

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
            if (getRequestParam(AttributeConst.TEL_DATE) == null
                    || getRequestParam(AttributeConst.TEL_DATE).equals("")) {
                day = LocalDateTime.now();
            } else {
                day = LocalDateTime.parse(getRequestParam(AttributeConst.TEL_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとに電話メモ情報のインスタンスを作成する
            TelView tv = new TelView(
                    null,
                    getRequestParam(AttributeConst.TEL_TITLE),
                    ev, //ログインしている従業員を、電話メモ作成者として登録する
                    day,
                    getRequestParam(AttributeConst.TEL_CUSTOMER),
                    empservice.findOne(toNumber(getRequestParam(AttributeConst.TEL_ATE_ID))),
                    getRequestParam(AttributeConst.TEL_CONTENT),
                    null,
                    null);

            //日報情報登録
            List<String> errors = telService.create(tv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TEL, tv);//入力された電話メモ情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_TEL_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TEL, ForwardConst.CMD_INDEX);
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
        TelView tv1 = telService.findOne(toNumber(getRequestParam(AttributeConst.TEL_ID)));

        if (tv1 == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TEL, tv1); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_TEL_SHOW);
        }
    }
    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に日報データを取得する
        TelView tv1 = telService.findOne(toNumber(getRequestParam(AttributeConst.TEL_ID)));

        //セッションからログイン中の従業員情報を取得
        TelView tv3 = (TelView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (tv3 == null || tv3.getId() != tv3.getEmployee().getId()) {
            //該当の日報データが存在しない、または
            //ログインしている従業員が日報の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.TEL, tv1); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_TEL_EDIT);
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
            TelView tv = telService.findOne(toNumber(getRequestParam(AttributeConst.TEL_ID)));

            //入力された日報内容を設定する
            tv.setTel_memo_date(toLocalDateTime(getRequestParam(AttributeConst.TEL_DATE)));
            tv.setTitle(getRequestParam(AttributeConst.TEL_TITLE));
            tv.setContent(getRequestParam(AttributeConst.TEL_CONTENT));

            //日報データを更新する
            List<String> errors = telService.update(tv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TEL, tv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_TEL_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TEL, ForwardConst.CMD_INDEX);

            }
        }
    }
}