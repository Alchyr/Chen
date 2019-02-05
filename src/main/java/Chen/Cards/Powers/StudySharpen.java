package Chen.Cards.Powers;

import Chen.Abstracts.ShiftChenCard;
import Chen.Powers.Sharpen;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import static Chen.ChenMod.makeID;

public class StudySharpen extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "StudySharpen",
            2,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int MAGIC_A = 2;
    private final static int MAGIC_B = 1;

    private final static int UPG_COST = 1;

    public StudySharpen()
    {
        super(cardInfo,false);

        setMagic(MAGIC_A, MAGIC_B);
        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Sharpen(p, this.magicNumber), this.magicNumber));
        }
    }
}