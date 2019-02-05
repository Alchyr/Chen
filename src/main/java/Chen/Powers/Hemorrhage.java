package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.ChenMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

public class Hemorrhage extends Power implements HealthBarRenderPower {
    public static final String NAME = "Hemorrhage";
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = false;

    public static final String POWER_ID = ChenMod.makeID(NAME);

    private static final Color hpColor = Color.YELLOW;

    public Hemorrhage(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, source, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);

        if (info.type == DamageInfo.DamageType.NORMAL)
        {
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new WaterDropEffect(owner.hb.cX + MathUtils.random(-5.0F * Settings.scale, 5.0F * Settings.scale), owner.hb.cY + MathUtils.random(-5.0F * Settings.scale, 5.0F * Settings.scale))));
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        updateDescription();
    }

    @Override
    public int getHealthBarAmount() {
        int attackAmount = 1;
        if (owner instanceof AbstractMonster)
        {
            if (((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_DEFEND)
            {
                attackAmount = (Integer)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "intentMultiAmt");
                if (attackAmount < 1)
                    attackAmount = 1;
            }
        }

        return this.amount * attackAmount;
    }

    @Override
    public Color getColor() {
        return hpColor;
    }

    @Override
    public void updateDescription() {
        int attackAmount = 1;
        if (owner instanceof AbstractMonster)
        {
            if (((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_BUFF ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                    ((AbstractMonster) owner).intent == AbstractMonster.Intent.ATTACK_DEFEND)
            {
                attackAmount = (Integer)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "intentMultiAmt");
                if (attackAmount < 1)
                    attackAmount = 1;
            }
        }

        if (attackAmount > 1)
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
            this.description += " NL " + descriptions[2] + "#b" + this.amount*attackAmount + descriptions[3] + "#b" + attackAmount + descriptions[4];
        }
        else
        {
            this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
        }
    }
}
