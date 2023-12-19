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
@Table(name = JpaConst.TABLE_REPL)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_REPL_GET_ALL,
            query = JpaConst.Q_REPL_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_REPL_COUNT,
            query = JpaConst.Q_REPL_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_REPL_GET_ALL_MINE,
            query = JpaConst.Q_REPL_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_REPL_COUNT_ALL_MINE,
            query = JpaConst.Q_REPL_COUNT_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Reply {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.REPL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * リプライを登録した従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.REPL_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * いつの電話メモかを示す日付
     */
    @Column(name = JpaConst.REPL_COL_TEL_DATE, nullable = false)
    private LocalDateTime reply_date;


    /**
     * 電話の宛先従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.REPL_COL_MIT_AT, nullable = false)
    private Employee mitayo_id;

    /**
     * 電話メモの内容
     */
    @Lob
    @Column(name = JpaConst.REPL_COL_CONTENT, nullable = false)
    private String content;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.REPL_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.REPL_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;


}
