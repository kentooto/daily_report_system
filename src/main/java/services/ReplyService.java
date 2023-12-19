package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReplyConverter;
import actions.views.ReplyView;
import constants.JpaConst;
import models.Reply;
import models.validators.ReplyValidator;

/**
 * 電話メモテーブルの操作に関わる処理を行うクラス
 */
public class ReplyService extends ServiceBase {

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReplyView> getMinePerPage(EmployeeView employee, int page) {

        List<Reply> reply = em.createNamedQuery(JpaConst.Q_REPL_GET_ALL_MINE, Reply.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReplyConverter.toViewList(reply);
    }

    /**
     * 指定した従業員が作成した電話メモデータの件数を取得し、返却する
     * @param employee
     * @return 電話メモデータの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REPL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReplyViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReplyView> getAllPerPage(int page) {

        List<Reply> reply = em.createNamedQuery(JpaConst.Q_REPL_GET_ALL, Reply.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReplyConverter.toViewList(reply);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reply_count = (long) em.createNamedQuery(JpaConst.Q_TEL_COUNT, Long.class)
                .getSingleResult();
        return reply_count;
    }


    /**
     * 画面から入力されたリプライの登録内容を元にデータを1件作成し、リプライに登録する
     * @param rlv リプライの登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ReplyView rlv) {
        List<String> errors = ReplyValidator.validate(rlv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rlv.setCreatedAt(ldt);
            rlv.setUpdatedAt(ldt);
            createInternal(rlv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param rv 日報の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ReplyView rlv) {

        //バリデーションを行う
        List<String> errors = ReplyValidator.validate(rlv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            rlv.setUpdatedAt(ldt);

            updateInternal(rlv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Reply findOneInternal(int id) {
        return em.find(Reply.class, id);
    }

    /**
     * 日報データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(ReplyView rlv) {

        em.getTransaction().begin();
        em.persist(ReplyConverter.toModel(rlv));
        em.getTransaction().commit();

    }

    /**
     * 日報データを更新する
     * @param rlv 日報データ
     */
    private void updateInternal(ReplyView rlv) {

        em.getTransaction().begin();
        Reply rv = findOneInternal(rlv.getId());
        ReplyConverter.copyViewToModel(rv, rlv);
        em.getTransaction().commit();

    }

    public ReplyView findOne(int number) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
