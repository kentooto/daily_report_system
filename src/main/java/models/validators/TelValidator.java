package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.TelView;
import constants.MessageConst;

/**
 * 電話メモインスタンスに設定されている値のバリデーションを行うクラス
 */
public class TelValidator {

    /**
     * 電話メモインスタンスの各項目についてバリデーションを行う
     * @param tv 電話メモインスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(TelView tv) {
        List<String> errors = new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(tv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        //内容のチェック
        String contentError = validateContent(tv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        //客先名のチェック
        String customerError = validateContent(tv.getCustomer());
        if (!customerError.equals("")) {
            errors.add(customerError);
        }


        return errors;
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOTITLE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
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