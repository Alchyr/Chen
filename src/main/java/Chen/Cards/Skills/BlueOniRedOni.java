package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Cards.Attacks.BlueOni;
import Chen.Cards.Attacks.RedOni;
import Chen.Character.Chen;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class BlueOniRedOni extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlueOniRedOni",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public BlueOniRedOni()
    {
        super(cardInfo, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        AbstractCard blueOni = new BlueOni();
        AbstractCard redOni = new RedOni();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(blueOni));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(redOni));
    }
}