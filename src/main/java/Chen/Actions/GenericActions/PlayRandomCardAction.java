package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PlayRandomCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private CardGroup cardSource;
    private Predicate<AbstractCard> condition;

    public PlayRandomCardAction(CardGroup source, boolean exhausts) {
        this(source, (c)->(true), exhausts);
    }
    public PlayRandomCardAction(CardGroup source, Predicate<AbstractCard> condition, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.cardSource = source;
        this.condition = condition;
        this.exhaustCards = exhausts;
    }

    public void update() {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();

        for (AbstractCard c : cardSource.group)
        {
            if (condition.test(c))
            {
                possibleCards.add(c);
            }
        }

        if (possibleCards.size() > 0)
        {
            AbstractCard card = possibleCards.get(AbstractDungeon.cardRandomRng.random(0, possibleCards.size() - 1));
            this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            card.freeToPlayOnce = true;
            card.exhaustOnUseOnce = this.exhaustCards;

            cardSource.group.remove(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);

            AbstractDungeon.player.limbo.group.add(card);
            if (cardSource.type != CardGroup.CardGroupType.HAND)
            {
                card.current_y = -200.0F * Settings.scale;
                card.drawScale = 0.12F;
            }
            card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.targetDrawScale = 0.75F;

            if (!card.canUse(AbstractDungeon.player, (AbstractMonster)this.target)) {
                if (this.exhaustCards) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                } else {
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
                }
            } else {
                card.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }
            if (cardSource.type == CardGroup.CardGroupType.HAND)
            {
                AbstractDungeon.actionManager.addToBottom(new UpdateHandAction());
            }
        }


        this.isDone = true;
    }
}