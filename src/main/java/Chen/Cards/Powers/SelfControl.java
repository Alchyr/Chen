package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class SelfControl extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SelfControl",
            1,
            AbstractCard.CardType.POWER,
            AbstractCard.CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 1;
    private final static int UPG_DRAW = 1;

    public SelfControl()
    {
        super(cardInfo, true);
        setMagic(DRAW, UPG_DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Chen.Powers.SelfControl(p, this.magicNumber), this.magicNumber));
    }
}