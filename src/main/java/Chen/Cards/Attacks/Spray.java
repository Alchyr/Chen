package Chen.Cards.Attacks;

import Chen.Abstracts.StandardSpell;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import static Chen.ChenMod.makeID;

public class Spray extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Spray",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 9;
    private final static int UPG_DAMAGE = 3;

    public Spray()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        isMultiDamage = true;
    }

    @Override
    public StandardSpell getCopyAsSpellCard() {
        return new Spray();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!mo.isDeadOrEscaped())
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(mo.hb.cX, mo.hb.cY)));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

    }
}