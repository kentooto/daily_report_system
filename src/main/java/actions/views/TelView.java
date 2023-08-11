package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Employee;

/**
 * 日報情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class TelView {

    /**
     * id
     */
    private Integer id;

    /**
     * 電話メモのタイトル
     */
    private String title;

    /**
     * 電話メモを登録した従業員
     */
    private EmployeeView employee;

    /**
     * いつの電話メモかを示す日付
     */
    private LocalDateTime tel_memo_date;

    /**
     * 客先名（電話番号）
     */
    private String customer;

    /**
     * 電話の宛先従業員
     */
    private Employee atesaki_id;

    /**
     * 電話メモの内容
     */
    private String content;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;
}
