package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Powers.ShikigamiFormBase;
import Chen.Powers.ShikigamiFormUpgraded;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class ShikigamiForm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ShikigamiForm",
            3,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BUFF = 1;

    public ShikigamiForm()
    {
        super(cardInfo, true);

        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShikigamiFormUpgraded(p, this.magicNumber), this.magicNumber));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShikigamiFormBase(p, this.magicNumber), this.magicNumber));
        }
    }
}