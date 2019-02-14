package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Powers.DamageUpPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

import static Chen.ChenMod.makeID;

public class FlankingStrike extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FlankingStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 3;

    public FlankingStrike()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);

        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Change effect based on form
        if (AbstractDungeon.player instanceof TwoFormCharacter)
        {
            if (((TwoFormCharacter) AbstractDungeon.player).Form) //human
            {
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY, 2), 0.25F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY, 2), 0.6F));
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.NONE));
            }
            else //cat
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DamageUpPower(p, 1), 1));
    }
}