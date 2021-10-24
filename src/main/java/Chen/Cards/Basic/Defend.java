package Chen.Cards.Basic;

import Chen.Abstracts.ShiftChenCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Defend extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Defend",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK_A = 5;
    private final static int BLOCK_B = 4;
    private final static int UPG_BLOCK_A = 3;
    private final static int UPG_BLOCK_B = 3;

    @Override
    public boolean canPlay(AbstractCard card) {
        return super.canPlay(card);
    }

    public Defend(boolean preview)
    {
        super(cardInfo, false, preview);

        setBlock(BLOCK_A, BLOCK_B, UPG_BLOCK_A, UPG_BLOCK_B);

        this.tags.add(CardTags.STARTER_DEFEND);
    }
    @Override
    public AbstractCard makeCopy() {
        return new Defend(true);
    }
    @Override
    protected ShiftChenCard noPreviewCopy() {
        return new Defend(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }
}