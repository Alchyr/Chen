package Chen.Cards.Skills;

import Chen.Abstracts.SwiftCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static Chen.ChenMod.makeID;

public class Sneak extends SwiftCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Sneak",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int BLOCK = 7;
    private final static int UPG_BLOCK = 3;
    private final static int DRAW = 1;

    public Sneak()
    {
        super(cardInfo, true);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
    }
}