package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Flit extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Flit",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 7;
    private final static int UPG_BLOCK = 3;

    private final static int DRAW = 2;

    private final static int DISCARD = 1;

    public Flit()
    {
        super(cardInfo, true);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD, !upgraded));
    }
}