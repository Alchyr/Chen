package Chen.Cards.Skills;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.GenericActions.PlayRandomCardAction;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Panic extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Panic",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public Panic()
    {
        super(cardInfo,true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new PlayRandomCardAction(AbstractDungeon.player.hand, (c)->(c.type==CardType.SKILL), !upgraded));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new PlayRandomCardAction(AbstractDungeon.player.hand, (c)->(c.type==CardType.ATTACK), !upgraded));
        }
    }
}