package G16.Controllers;

import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Graphics.MatadorGUI;
import G16.Language;
import G16.PlayerUtils.Player;
import G16.PlayerUtils.TradeOffer;

import java.util.ArrayList;
import java.util.Objects;


/** Trade controller for managing player to player trading
 * @author G16
 * @version 0.5
 */

public class TradeController {

    private final MatadorGUI mgui;
    private final GameController gc;


    public TradeController(MatadorGUI mgui, GameController gc) {
        this.mgui = mgui;
        this.gc = gc;
    }

    /** Start trade
     * @param currentPlayer player who initializes the trade
     */
    public void startTrade(Player currentPlayer) {

        ArrayList<Player> players = gc.getPlayers();
        players.removeIf(Player::getBankrupt);
        ArrayList<String> options = new ArrayList<>();
        for(Player p : players){
            options.add(p.getName());
        }
        options.removeIf(player -> currentPlayer.getName().equals(player));
        options.add(Language.getString("cancelTrade"));

        String action = mgui.requestUserDropDown(Language.getString("selectTradeReceiver"), options.toArray(new String[0]));
        if(!action.equals(Language.getString("cancelTrade"))){
            Player tradeReceiver = null;
            for(Player p : players){
                if(p.getName().equals(action)){
                    tradeReceiver = p;
                }
            }
            if(tradeReceiver != null){
                TradeOffer playerOffer = makeTradeOffer(currentPlayer, tradeReceiver);

                if(playerOffer.getFinished()){
                    String message = tradeReceiver.getName() + " - " + Language.getString("tradeOfferWantToAccept") + "\n" + getTradeStatusText(playerOffer);
                    String askAcceptTrade = mgui.requestUserButton(message, Language.getString("yesTxt"), Language.getString( "noTxt"));
                    if(askAcceptTrade.equals(Language.getString("yesTxt"))){
                        gc.addBalanceToPlayer(playerOffer.getTradeParty(true), playerOffer.getMoney(true));
                        gc.addBalanceToPlayer(playerOffer.getTradeParty(false), playerOffer.getMoney(false));
                        for(BuyableField tradedField : playerOffer.getFields(true)){
                            tradedField.setOwner(tradeReceiver);
                            mgui.setOwner(tradedField, tradedField.getID());
                            if(tradedField instanceof ShippingCompany){
                                tradeReceiver.setShipsOwned(tradeReceiver.getShipsOwned()+1);
                                currentPlayer.setShipsOwned(currentPlayer.getShipsOwned()-1);
                            }
                            if(tradedField instanceof Brewery){
                                tradeReceiver.setBrewsOwned(tradeReceiver.getBrewsOwned()+1);
                                currentPlayer.setBrewsOwned(currentPlayer.getBrewsOwned()-1);
                            }
                        }
                        for(BuyableField tradedField : playerOffer.getFields(false)){
                            tradedField.setOwner(currentPlayer);
                            mgui.setOwner(tradedField, tradedField.getID());
                            if(tradedField instanceof ShippingCompany){
                                tradeReceiver.setShipsOwned(tradeReceiver.getShipsOwned()-1);
                                currentPlayer.setShipsOwned(currentPlayer.getShipsOwned()+1);
                            }
                            if(tradedField instanceof Brewery){
                                tradeReceiver.setBrewsOwned(tradeReceiver.getBrewsOwned()-1);
                                currentPlayer.setBrewsOwned(currentPlayer.getBrewsOwned()+1);
                            }
                        }
                        mgui.showMessage(Language.getString("tradeOfferWasAccepted"));
                        return;
                    }

                }

            }

        }

        mgui.showMessage(Language.getString("tradeOfferWasCancelled"));
    }


    /** Make trade offer
     * @param proposer player who initializes the trade and suggests the trade
     * @param receiver the player to whom the proposer wishes to trade
     * @return TradeOffer object with proposed trade offer info
     */
    private TradeOffer makeTradeOffer(Player proposer, Player receiver){
        TradeOffer newOffer = new TradeOffer(proposer, receiver);
        String action;
        do{
            //Write current trade status
            String tradeStatusText = getTradeStatusText(newOffer);


            action = mgui.requestUserButton(Language.getString("createTradeOffer") +tradeStatusText ,
                    Language.getString("tradeOfferSetMoney"),
                    Language.getString("tradeOfferSetField"),
                    Language.getString("tradeOfferSendOffer"),
                    Language.getString("tradeOfferCancel")
            );
            if(action.equals(Language.getString("tradeOfferSetMoney"))){
                int moneyTransactionAmount = mgui.requestInteger(Language.getString("tradeOfferHowMuchMoney"),-1 * receiver.getPlayerBalance(), proposer.getPlayerBalance());
                newOffer.setMoney(-moneyTransactionAmount, true);
                newOffer.setMoney(moneyTransactionAmount, false);
            }
            if(action.equals(Language.getString("tradeOfferSetField"))){
                action = mgui.requestUserButton(Language.getString("tradeOfferChooseFieldGiver"), proposer.getName(), receiver.getName());

                tradePickOfferedFields(newOffer, Objects.equals(action, proposer.getName()));


            }
            if(action.equals(Language.getString("tradeOfferSendOffer"))){
                newOffer.setFinished(true);
            }
        } while (!action.equals(Language.getString("tradeOfferCancel")) && !action.equals(Language.getString("tradeOfferSendOffer")));
        return newOffer;
    }


    /** Make player pick offered fields
     * @param newOffer the trade offer which is being made
     * @param proposer is the player adding fields on behalf of himself(the proposer) or the receiver of the trade offer?
     *
     */
    private void tradePickOfferedFields(TradeOffer newOffer, boolean proposer) {
        Player trader = newOffer.getTradeParty(proposer);
        ArrayList<BuyableField> offeredFields = newOffer.getFields(proposer);
        ArrayList<BuyableField> ownedFields = gc.getOwnedBuyableFields(trader);
        ownedFields.removeIf(offeredFields::contains);

        boolean continueAdding;
        do{
            continueAdding = false;
            ArrayList<String> options = new ArrayList<>();
            for(BuyableField bf : ownedFields){
                options.add(bf.getName());
            }
            options.add(Language.getString("tradeOfferAddFieldGoBack"));
            String addedField = mgui.requestUserDropDown(trader.getName() + " " + Language.getString("tradeOfferAddFieldText"),options.toArray(new String[0]));
            if(!addedField.equals(Language.getString("tradeOfferAddFieldGoBack"))){
                BuyableField selectedField = null;
                for(BuyableField offerableFields : ownedFields){
                    if(offerableFields.getName().equals(addedField)){
                        selectedField = offerableFields;
                    }
                }
                offeredFields.add(selectedField);
                ownedFields.remove(selectedField);

                StringBuilder listOfOfferedFields = new StringBuilder();
                for(BuyableField fieldOffer : offeredFields){
                    listOfOfferedFields.append(fieldOffer.getName()).append("\n");
                }

                String askContinue = mgui.requestUserButton(Language.getString("tradeOfferAddMoreFieldQ") + "\n" + listOfOfferedFields, Language.getString("yesTxt"),Language.getString("noTxt"));
                if(askContinue.equals(Language.getString("yesTxt"))){
                    continueAdding = true;
                }
                newOffer.setFields(offeredFields, proposer);
            }

        } while (continueAdding);
    }


    /** Generate string to display trade offer
     * @param newOffer the trade offer which is to be displayed
     * @return String with formatted text
     *
     */
    private String getTradeStatusText(TradeOffer newOffer) {
        StringBuilder proposerBuyableFieldsOffered = new StringBuilder();
        StringBuilder receiverBuyableFieldsOffered = new StringBuilder();
        for(BuyableField offerFields : newOffer.getFields(true)){
            proposerBuyableFieldsOffered.append(offerFields.getName()).append(" - ");
        }
        for(BuyableField offerFields : newOffer.getFields(false)){
            receiverBuyableFieldsOffered.append(offerFields.getName()).append("\n");
        }
        String text = "\n " + newOffer.getTradeParty(true).getName() +" - "
                + ((newOffer.getMoney(true) > 0) ? Language.getString("tradeOfferGetMoneyText") : Language.getString("tradeOfferGiveMoneyText"))
                +": "+ Math.abs(newOffer.getMoney(true)) + " - "
                + Language.getString("tradeOfferFields") +": " + proposerBuyableFieldsOffered +

                "\n " + newOffer.getTradeParty(false).getName() +" - " +
                ((newOffer.getMoney(false) > 0) ? Language.getString("tradeOfferGetMoneyText") : Language.getString("tradeOfferGiveMoneyText")) +
                ": " + Math.abs(newOffer.getMoney(false)) + " - "
                + Language.getString("tradeOfferFields") +": " + receiverBuyableFieldsOffered;
        return text;
    }

}
