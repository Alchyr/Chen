package Chen.Powers;

import Chen.ChenMod;
import Chen.Patches.HiDefPowerPatch;
import Chen.Util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Chen.Util.PowerImages.PowerPath;

public class PossessionPower extends TwoAmountPower {
    public static final String POWER_ID = ChenMod.makeID("Possession");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ChenMod.assetPath(PowerPath(NAME));

    public PossessionPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = TextureLoader.getTexture(IMG);

        Texture HiDefImage = TextureLoader.getHiDefPowerTexture(this.name);
        if (HiDefImage != null)
            HiDefPowerPatch.HiDefImage.img84.set(this, HiDefImage);
    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, this.amount), this.amount));
        this.amount2 += amount;
        updateDescription();
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0 && this.amount2 > 0)
        {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.amount2), -this.amount2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount2), -this.amount2));
            this.amount2 = 0;
            updateDescription();
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + "#b" + this.amount
                + DESCRIPTIONS[1] + "#b" + this.amount2 + DESCRIPTIONS[2];
    }
}