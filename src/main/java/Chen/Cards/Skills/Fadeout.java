package Chen.Cards.Skills;

import Chen.Abstracts.StandardSpell;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Chen.ChenMod.makeID;

public class Fadeout extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Fadeout",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 7;
    private final static int UPG_BLOCK = 3;

    private final static int DEBUFF = 2;

    public Fadeout()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(DEBUFF);

        setExhaust(true);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Fadeout();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.blockUpgrade));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber));
        }
    }
}