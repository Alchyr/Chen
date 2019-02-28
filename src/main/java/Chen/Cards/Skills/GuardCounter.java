package Chen.Cards.Skills;

import Chen.Abstracts.ShiftChenCard;
import Chen.Powers.CounterPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static Chen.ChenMod.makeID;

public class GuardCounter extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GuardCounter",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 6;
    private final static int UPG_BLOCK = 2;

    private final static int BUFF_A = 2;
    private final static int BUFF_B = 2;
    private final static int UPG_BUFF_A = 1;
    private final static int UPG_BUFF_B = 1;

    public GuardCounter()
    {
        super(cardInfo, false);

        setBlock(BLOCK, BLOCK, UPG_BLOCK, UPG_BLOCK);
        setMagic(BUFF_A, BUFF_B, UPG_BUFF_A, UPG_BUFF_B);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CounterPower(p, this.magicNumber), this.magicNumber));
        }
    }
}