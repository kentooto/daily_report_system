package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Reply;

/**
 * 電話メモのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class ReplyConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rlv ReplyViewのインスタンス
     * @return Replyのインスタンス
     */
    public static Reply toModel(ReplyView rlv) {
        return new Reply(
                rlv.getId(),
                EmployeeConverter.toModel(rlv.getEmployee()),
                rlv.getReply_date(),
                EmployeeConverter.toModel(rlv.getMitayo_id()),
                rlv.getContent(),
                rlv.getCreatedAt(),
                rlv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param rv Replyのインスタンス
     * @return ReplyViewのインスタンス
     */
    public static ReplyView toView(Reply rv) {

        if (rv == null) {
            return null;
        }

        return new ReplyView(
                rv.getId(),
                EmployeeConverter.toView(rv.getEmployee()),
                rv.getReply_date(),
                EmployeeConverter.toView(rv.getMitayo_id()),
                rv.getContent(),
                rv.getCreatedAt(),
                rv.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReplyView> toViewList(List<Reply> list) {
        List<ReplyView> rvv = new ArrayList<>();

        for (Reply rv : list) {
            rvv.add(toView(rv));
        }

        return rvv;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param t DTOモデル(コピー先)
     * @param tv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Reply rv, ReplyView rlv) {
        rv.setId(rv.getId());
        EmployeeConverter.toView(rv.getEmployee());
        rv.setReply_date(rlv.getReply_date());
        EmployeeConverter.toView(rv.getMitayo_id());
        rv.setContent(rlv.getContent());
        rv.setCreatedAt(rlv.getCreatedAt());
        rv.setUpdatedAt(rlv.getUpdatedAt());

    }

}
