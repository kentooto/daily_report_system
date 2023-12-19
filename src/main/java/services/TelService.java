package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.TelConverter;
import actions.views.TelView;
import constants.JpaConst;
import models.Tel_memo;
import models.validators.TelValidator;

/**
 * 電話メモテーブルの操作に関わる処理を行うクラス
 */
public class TelService extends ServiceBase {

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<TelView> getMinePerPage(EmployeeView employee ) {

        List<Tel_memo> tel_memo = em.createNamedQuery(JpaConst.Q_TEL_GET_ALL_MINE, Tel_memo.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getResultList();
        return TelConverter.toViewList(tel_memo);
    }

    /**
     * 指定した従業員が作成した電話メモデータの件数を取得し、返却する
     * @param employee
     * @return 電話メモデータの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_TEL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<TelView> getAllPerPage(int telpage) {

        List<Tel_memo> tel_memo = em.createNamedQuery(JpaConst.Q_TEL_GET_ALL, Tel_memo.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (telpage - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return TelConverter.toViewList(tel_memo);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long tels_count = (long) em.createNamedQuery(JpaConst.Q_TEL_COUNT, Long.class)
                .getSingleResult();
        return tels_count;
    }


    /**
     * 画面から入力された電話メモの登録内容を元にデータを1件作成し、電話メモに登録する
     * @param rv 電話メモの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(TelView tv) {
        List<String> errors = TelValidator.validate(tv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            tv.setCreatedAt(ldt);
            tv.setUpdatedAt(ldt);
            createInternal(tv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(TelView tv) {

        //バリデーションを行う
        List<String> errors = TelValidator.validate(tv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            tv.setUpdatedAt(ldt);

            updateInternal(tv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Tel_memo findOneInternal(int id) {
        return em.find(Tel_memo.class, id);
    }

    /**
     * 日報データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(TelView tv) {

        em.getTransaction().begin();
        em.persist(TelConverter.toModel(tv));
        em.getTransaction().commit();

    }

    /**
     * 日報データを更新する
     * @param rv 日報データ
     */
    private void updateInternal(TelView tv) {

        em.getTransaction().begin();
        Tel_memo t = findOneInternal(tv.getId());
        TelConverter.copyViewToModel(t, tv);
        em.getTransaction().commit();

    }


    public TelView findOne(int id) {
        return TelConverter.toView(findOneInternal(id));

    }

}
