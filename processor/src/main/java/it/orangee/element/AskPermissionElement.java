package it.orangee.element;




/*

   ____                _         _           
  / ___|_ __ ___  __ _| |_ ___  | |__  _   _ 
 | |   | '__/ _ \/ _` | __/ _ \ | '_ \| | | |
 | |___| | |  __/ (_| | |_  __/ | |_) | |_| |
  \____|_|  \___|\__,_|\__\___| |_.__/ \__, |
                                       |___/                                                                      

  ____             _         _                    
 |  _ \ _   _ _ __(_) ___   | |   _   _  ___ __ _ 
 | |_) | | | | '__| |/ _ \  | |  | | | |/ __/ _` |
 |  _ <| |_| | |  | | (_) | | |___ |_| | (__ (_| |
 |_| \_\\__,_|_|  |_|\___/  |_____\__,_|\___\__,_|
  
  
  07/02/2019                 

*/

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import it.orangee.annotation.AskPermission;


public abstract class AskPermissionElement<T extends Element> {

    T element;

    AskPermission askPermission;

    AskPermissionElement(final T element) {
        this.element = element;
        this.askPermission = element.getAnnotation(AskPermission.class);
    }

    public AskPermission getAskPermission() {
        return askPermission;
    }

    public abstract String getClassName();

    abstract TypeElement getActivity();

    public T getElement() {
        return element;
    }

}
