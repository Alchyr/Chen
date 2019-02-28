package Chen.Actions.ChenActions;

import Chen.Abstracts.AbstractXAction;
import Chen.Actions.GenericActions.VFXIfAliveAction;
import Chen.Powers.Hemorrhage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.*;

public class ImpaleAction extends AbstractXAction {
    private int damage;
    private int hemorrhage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;

    public ImpaleAction(AbstractPlayer p, AbstractMonster m, int hemorrhage, int damage, DamageInfo.DamageType damageTypeForTurn)
    {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.damageTypeForTurn = damageTypeForTurn;
        this.hemorrhage = hemorrhage;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (amount > 0) {
            for(int i = 0; i < amount; ++i) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(m.hb.cX + MathUtils.random(-20.0f, 20.0f) * Settings.scale, m.hb.cY, true)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.hemorrhage), this.hemorrhage));
            }
        }

        this.isDone = true;
    }
}