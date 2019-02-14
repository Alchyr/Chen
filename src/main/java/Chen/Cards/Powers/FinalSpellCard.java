package Chen.Cards.Powers;

import Chen.Abstracts.BaseCard;
import Chen.Powers.FinalSpellPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class FinalSpellCard extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FinalSpellCard",
            4,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private static final int BUFF = 1;

    public FinalSpellCard()
    {
        super(cardInfo, true);

        setMagic(BUFF);
        isEthereal = true;
    }

    @Override
    public void tookDamage() {
        this.updateCost(-1);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        isEthereal = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FinalSpellPower(p, this.magicNumber), this.magicNumber));
    }
}