package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Powers.PossessionPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Possession extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Possession",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BUFF = 1;
    private static final int UPG_BUFF = 1;

    public Possession()
    {
        super(cardInfo, false);

        setMagic(BUFF, UPG_BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PossessionPower(p, this.magicNumber), this.magicNumber));
    }
}