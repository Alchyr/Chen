package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Powers.PreventAllBlockPower;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Barrier extends BaseCard implements BlockSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Barrier",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 8;
    private final static int UPG_BLOCK = 3;

    public Barrier()
    {
        super(cardInfo, false);

        setMagic(BLOCK, UPG_BLOCK);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Barrier();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, SpellDamage.getSpellDamage(this)));
    }
}