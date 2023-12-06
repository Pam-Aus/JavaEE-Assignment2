/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.austin.bouncer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author chiaustin
 * This Bouncer class is an entity class which represents bouncer instances and the Bouncer 
 * table in the database.
 */
@Entity
@Table (name = "Bouncer")
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //unique instance id
    
    @NotNull
    private Integer xPosition; //horizontal instance position
    
    @NotNull
    private Integer yPosition; // vertical instance position
    
    @NotNull
    private Integer yVelocity; // vertical velocity
    
    private static final int DECAY_RATE = 1;
    
    private static final int FRAME_HEIGHT = 600; // default height of frame

    /**
     * Method returns the id of an instance
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Method sets the id of an instance
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Method returns horizontal position of instance
     * @return  xPosition
     */
    public Integer getXPosition(){
        return this.xPosition;
    }
    
    /**
     * Method sets the x position of an instance
     * @param x 
     */
    public void setXPosition(Integer x){
        this.xPosition = x;
    }
    
    /**
     * Method returns the y position of instance
     * @return y
     */
    public Integer getYPosition(){
        return this.yPosition;
    }
    
    /**
     * Method sets the y position of an instance
     * @param y 
     */
    public void setYPosition(Integer y){
        this.yPosition = y;
    }
    
    /**
     * Method returns the falling vertical speed of an instance
     * @return yVelocity
     */
    public Integer getYVelocity(){
        return this.yVelocity;
    }
    
    /**
     * Method sets the falling vertical speed of an instance
     * @param v 
     */
    public void setYVelocity(Integer v){
        this.yVelocity = v;
    }
    
    /**
     * This method updated the vertical position of an instance under gravity
     * The y velocity increases as the instance's y position approaches the frame height
     */
    public void advanceOneFrame(){
        if (yPosition > 0){
            yVelocity += 1;
        }
        
        yPosition += yVelocity;
        
        if( yPosition >= FRAME_HEIGHT){
            yPosition = FRAME_HEIGHT;
            if(yVelocity > 0){
                yVelocity = -Math.abs(yVelocity - DECAY_RATE);
            }
        }

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.austin.bouncer.Bouncer[ id=" + id + " ]";
    }
    
}
