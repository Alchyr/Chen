package Chen.Cards.Skills;

import Chen.Abstracts.SwiftCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Rush extends SwiftCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rush",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 2;
    private final static int UPG_DRAW = 1;

    public Rush()
    {
        super(cardInfo, false);

        setMagic(DRAW, UPG_DRAW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
}