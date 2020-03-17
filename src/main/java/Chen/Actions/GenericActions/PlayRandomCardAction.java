package Chen.Actions.GenericActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
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

        AbstractMonster cardTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        for (AbstractCard c : cardSource.group)
        {
            if (condition.test(c) && c.canUse(AbstractDungeon.player, cardTarget))
            {
                possibleCards.add(c);
            }
        }
        if (possibleCards.isEmpty() && !cardSource.group.isEmpty()) //prioritize usable cards first
        {
            for (AbstractCard c : cardSource.group)
            {
                if (condition.test(c))
                {
                    possibleCards.add(c);
                }
            }
        }

        if (possibleCards.size() > 0)
        {
            AbstractCard card = possibleCards.get(AbstractDungeon.cardRandomRng.random(0, possibleCards.size() - 1));

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

            card.applyPowers();
            this.addToTop(new NewQueueCardAction(card, true, false, true));
            this.addToTop(new UnlimboAction(card));
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }

            if (cardSource.type == CardGroup.CardGroupType.HAND)
            {
                AbstractDungeon.actionManager.addToBottom(new UpdateHandAction());
            }
        }


        this.isDone = true;
    }
}