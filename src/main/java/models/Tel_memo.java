package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 電話メモのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TEL)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TEL_GET_ALL,
            query = JpaConst.Q_TEL_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_TEL_COUNT,
            query = JpaConst.Q_TEL_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_TEL_GET_ALL_MINE,
            query = JpaConst.Q_TEL_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_TEL_COUNT_ALL_MINE,
            query = JpaConst.Q_TEL_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Tel_memo {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TEL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 電話メモのタイトル
     */
    @Column(name = JpaConst.TEL_COL_TITLE, length = 100, nullable = false)
    private String title;

    /**
     * 電話メモを登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TEL_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * いつの電話メモかを示す日付
     */
    @Column(name = JpaConst.TEL_COL_TEL_DATE, nullable = false)
    private LocalDateTime tel_memo_date;

    /**
     * 客先名（電話番号）
     */
    @Column(name = JpaConst.TEL_COL_CUSTOMER, length = 100, nullable = false)
    private String customer;

    /**
     * 電話の宛先従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TEL_COL_ATE, nullable = false)
    private Employee atesaki_id;

    /**
     * 電話メモの内容
     */
    @Lob
    @Column(name = JpaConst.TEL_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.TEL_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.TEL_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;


}
