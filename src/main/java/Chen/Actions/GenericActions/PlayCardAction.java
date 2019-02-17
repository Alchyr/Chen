package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private AbstractCard card;
    private CardGroup sourceGroup;

    public PlayCardAction(AbstractCard cardToUse, CardGroup source, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.card = cardToUse;
        this.sourceGroup = source;
        this.exhaustCards = exhausts;
    }

    public void update() {
        card.freeToPlayOnce = true;
        card.exhaustOnUseOnce = this.exhaustCards;

        AbstractMonster cardTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        AbstractDungeon.actionManager.addToTop(new UpdateHandAction());

        if (sourceGroup == null)
        {
            if (target != null) {
                card.calculateCardDamage(cardTarget);
            }
            AbstractDungeon.player.limbo.addToBottom(card);
            card.current_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.current_y = (float)Settings.HEIGHT / 2.0F;
            card.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;

            card.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, cardTarget, AbstractDungeon.player.energy.energy));
        }
        else if (!card.canUse(AbstractDungeon.player, cardTarget)) {
            if (this.exhaustCards) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, sourceGroup));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, sourceGroup));
            }
        } else {
            card.applyPowers();
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, cardTarget));
        }


        this.isDone = true;
    }
}