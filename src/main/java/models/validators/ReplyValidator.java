package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ReplyView;
import constants.MessageConst;

/**
 * 電話メモインスタンスに設定されている値のバリデーションを行うクラス
 */
public class ReplyValidator {

    /**
     * 電話メモインスタンスの各項目についてバリデーションを行う
     * @param rlv 電話メモインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(ReplyView rlv) {
        List<String> errors = new ArrayList<String>();


        //内容のチェック
        String contentError = validateContent(rlv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }


        return errors;
    }


    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content 内容
     * @return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}