package actions;

import java.io.IOException;
import java.util.List; //追記

import javax.servlet.ServletException;

import actions.views.EmployeeView; //追記
import actions.views.ReportView; //追記
import actions.views.TelView; //追記
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst; //追記
import services.ReportService; //追記
import services.TelService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private ReportService reportservice; //追記
    private TelService telservice;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        reportservice = new ReportService(); //追記
        telservice = new TelService();
        //メソッドを実行
        invoke();

        reportservice.close();
        telservice.close(); //追記

    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        // 以下追記

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // 電話メモ作成後ほど作成

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        int reportpage = getPage();
        List<ReportView> reports = reportservice.getMinePerPage(loginEmployee, reportpage);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myReportsCount = reportservice.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, myReportsCount); //ログイン中の従業員が作成した日報の数
        putRequestScope(AttributeConst.PAGE, reportpage); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        // 電話メモ作成後ほど作成

        //ログイン中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
        List<TelView> Tels = telservice.getMinePerPage(loginEmployee);

        //ログイン中の従業員が作成した日報データの件数を取得
        long myTelsCount = telservice.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.TELS, Tels); //取得した日報データ
        putRequestScope(AttributeConst.TEL_COUNT, myTelsCount); //ログイン中の従業員が作成した日報の数
        //↑ここまで追記

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}