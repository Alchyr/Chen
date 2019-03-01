package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Powers.Unbalanced;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Stability extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Stability",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 13;
    private final static int UPG_BLOCK = 5;

    public Stability()
    {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Chen.Powers.Stability(p), 0));
    }
}