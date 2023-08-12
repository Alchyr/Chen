package Chen.Cards.Skills;

import Chen.Abstracts.StandardSpell;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import static Chen.ChenMod.makeID;

public class Barrier extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Barrier",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 6;
    private final static int UPG_BLOCK = 3;

    public Barrier()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public StandardSpell getCopyAsSpellCard() {
        return new Barrier();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        this.addToBot(new ApplyPowerAction(p, p, new EquilibriumPower(p, 1), 1));
    }
}