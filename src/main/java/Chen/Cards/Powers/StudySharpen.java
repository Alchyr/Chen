package Chen.Cards.Powers;

import Chen.Abstracts.ShiftChenCard;
import Chen.Powers.Sharpen;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

    private final static int MAGIC_A = 3;
    private final static int UPG_MAGIC_A = 1;
    private final static int MAGIC_B = 1;
    private final static int UPG_MAGIC_B = 1;

    public StudySharpen(boolean preview)
    {
        super(cardInfo,false, preview);

        setMagic(MAGIC_A, UPG_MAGIC_A, MAGIC_B, UPG_MAGIC_B);
    }
    @Override
    public AbstractCard makeCopy() {
        return new StudySharpen(true);
    }
    @Override
    protected ShiftChenCard noPreviewCopy() {
        return new StudySharpen(false);
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