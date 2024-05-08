/*
 * Created on 2023-07-05 ( Time 13:10:58 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
// This Bean has a basic Primary Key (not composite) 

package ci.smile.simswaporange.dao.entity;

import java.io.Serializable;

import lombok.*;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "historique"
 *
 * @author Telosys Tools Generator
 *
 */
@Data
@ToString
@Entity
@Table(name="historique" )
public class Historique implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="request", length=2147483647)
    private String     request      ;

    @Column(name="response", length=2147483647)
    private String     response     ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date       createdAt    ;

    @Column(name="created_by")
    private Integer    createdBy    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date       updatedAt    ;

    @Column(name="updated_by")
    private Integer    updatedBy    ;

    @Column(name="is_deleted")
    private Boolean    isDeleted    ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date")
    private Date       date         ;

    @Column(name="action_service", length=255)
    private String     actionService ;

    @Column(name="login", length=255)
    private String     login        ;

    @Column(name="numero", length=255)
    private String     numero        ;

    @Column(name="nom", length=255)
    private String     nom          ;

    @Column(name="prenom", length=255)
    private String     prenom       ;

    @Column(name="username", length=255)
    private String     username        ;

    @Column(name="name", length=255)
    private String     name          ;

    @Column(name="firstname", length=255)
    private String     firstname       ;

    @Column(name="raison", length=2147483647)
    private String     raison       ;

    @Column(name="is_connexion")
    private Boolean    isConnexion  ;

    @Column(name="status_connection", length=100)
    private String     statusConnection ;

    @Column(name="machine", length=255)
    private String     machine      ;

    @Column(name="ipadress", length=100)
    private String     ipadress     ;

    @Column(name="uri", length=255)
    private String     uri     ;



	// "idActionUser" (column "id_action_user") is not defined by itself because used as FK in a link 

    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="id_action_user", referencedColumnName="id")
    private ActionSimswap actionSimswap;

    @ManyToOne
    @JoinColumn(name="id_user", referencedColumnName="id")
    private User user    ;

    @ManyToOne
    @JoinColumn(name="id_status", referencedColumnName="id")
    private Status status    ;

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public Historique() {
		super();
    }
    
	//----------------------------------------------------------------------
    // clone METHOD
    //----------------------------------------------------------------------
	@Override
	public java.lang.Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
