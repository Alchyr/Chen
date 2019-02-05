package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Powers.BlockOnShift;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class UnstableBoundary extends BaseCard implements BlockSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "UnstableBoundary",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 5;
    private final static int UPG_BLOCK = 2;

    public UnstableBoundary()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new UnstableBoundary();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlockOnShift(p, p, this.block), this.block));
    }
}