package Chen.Cards.Skills;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.ChenActions.ConditionalCardEnergyGain;
import Chen.Actions.GenericActions.DrawAndSaveCardsAction;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Nimbleness extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Nimbleness",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DRAW = 2;
    private final static int UPG_DRAW = 1;

    public Nimbleness(boolean preview)
    {
        super(cardInfo, false, preview);

        setMagic(DRAW, DRAW, UPG_DRAW, UPG_DRAW);
    }
    @Override
    public AbstractCard makeCopy() {
        return new Nimbleness(true);
    }
    @Override
    protected ShiftChenCard noPreviewCopy() {
        return new Nimbleness(false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        DrawAndSaveCardsAction cardDraw = new DrawAndSaveCardsAction(p, this.magicNumber);

        CardType ConditionType = Form ? CardType.SKILL : CardType.ATTACK;

        AbstractDungeon.actionManager.addToBottom(cardDraw);
        AbstractDungeon.actionManager.addToBottom(new ConditionalCardEnergyGain(cardDraw, (c)->c.type==ConditionType));
    }
}