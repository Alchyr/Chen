package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Interfaces.SpellCard;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.SneckoEye;

import static Chen.ChenMod.makeID;

public class PentagramFlight extends BaseCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PentagramFlight",
            1,
            CardType.SKILL,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DEBUFF = 2;
    private final static int SELF_DEBUFF = 1;
    private final static int UPGRADE_COST = 0;

    public PentagramFlight()
    {
        super(cardInfo, false);

        setCostUpgrade(UPGRADE_COST);
        setMagic(DEBUFF);

    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new PentagramFlight();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Disoriented(mo, SpellDamage.getSpellDamage(this)), SpellDamage.getSpellDamage(this)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, -SELF_DEBUFF), -SELF_DEBUFF));
    }
}