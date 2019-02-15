package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.GenericActions.DoublePowerAction;
import Chen.Powers.CopyCardPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Copycat extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Copycat",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public final static int UPG_COST = 0;
    public final static int BUFF = 1;

    public Copycat()
    {
        super(cardInfo, false);
        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CopyCardPower(p, this.magicNumber), this.magicNumber));
    }
}