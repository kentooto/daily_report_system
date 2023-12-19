package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Tel_memo;

/**
 * 電話メモのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TelConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param tv TelViewのインスタンス
     * @return Tel_memoのインスタンス
     */
    public static Tel_memo toModel(TelView tv) {
        return new Tel_memo(
                tv.getId(),
                tv.getTitle(),
                EmployeeConverter.toModel(tv.getEmployee()),
                tv.getTel_memo_date(),
                tv.getCustomer(),
                EmployeeConverter.toModel(tv.getAtesaki_id()),
                tv.getContent(),
                tv.getCreatedAt(),
                tv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param t Tel_memoのインスタンス
     * @return TelViewのインスタンス
     */
    public static TelView toView(Tel_memo t) {

        if (t == null) {
            return null;
        }

        return new TelView(
                t.getId(),
                t.getTitle(),
                EmployeeConverter.toView(t.getEmployee()),
                t.getTel_memo_date(),
                t.getCustomer(),
                EmployeeConverter.toView(t.getAtesaki_id()),
                t.getContent(),
                t.getCreatedAt(),
                t.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TelView> toViewList(List<Tel_memo> list) {
        List<TelView> tvs = new ArrayList<>();

        for (Tel_memo t : list) {
            tvs.add(toView(t));
        }

        return tvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param t DTOモデル(コピー先)
     * @param tv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Tel_memo t, TelView tv) {
        t.setId(tv.getId());
        EmployeeConverter.toView(t.getEmployee());
        t.setTitle(tv.getTitle());
        t.setTel_memo_date(tv.getTel_memo_date());
        t.setCustomer(tv.getCustomer());
        EmployeeConverter.toView(t.getAtesaki_id());
        t.setContent(tv.getContent());
        t.setCreatedAt(tv.getCreatedAt());
        t.setUpdatedAt(tv.getUpdatedAt());

    }

}
