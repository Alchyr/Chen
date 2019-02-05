package Chen.Cards.Powers;

import Chen.Abstracts.ShiftChenCard;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Evasive;
import Chen.Powers.Rapidfire;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class RapidfireEvasive extends ShiftChenCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "RapidfireEvasive",
            1,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int MAGIC_A = 3;
    private final static int MAGIC_B = 3;
    private final static int UPG_MAGIC_A = 1;
    private final static int UPG_MAGIC_B = 1;

    public RapidfireEvasive()
    {
        super(cardInfo,false);

        setMagic(MAGIC_A, MAGIC_B, UPG_MAGIC_A, UPG_MAGIC_B);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        RapidfireEvasive returnCard = new RapidfireEvasive();
        returnCard.Shift(Chen.ChenHuman);
        return returnCard;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Rapidfire(p, SpellDamage.getSpellDamage(this)), SpellDamage.getSpellDamage(this)));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Evasive(p, this.magicNumber), this.magicNumber));
        }
    }
}