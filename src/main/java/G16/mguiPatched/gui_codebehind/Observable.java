package G16.mguiPatched.gui_codebehind;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Observable {
   private ArrayList<Observer> observers = new ArrayList();

   public Observable() {
   }

   public void addObserver(Observer obs) {
      this.observers.add(obs);
   }

   protected void notifyObservers() {
      Iterator var1 = this.observers.iterator();

      while(var1.hasNext()) {
         Observer obs = (Observer)var1.next();
         obs.onUpdate();
      }

   }
}
