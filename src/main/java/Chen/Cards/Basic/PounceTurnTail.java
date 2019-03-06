package Chen.Cards.Basic;

import Chen.Abstracts.ShiftChenCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Effects.CustomVerticalImpactEffect;
import Chen.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class PounceTurnTail extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PounceTurnTail",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 8;
    private final static int DAMAGE_B = 0;
    private final static int UPG_DAMAGE_A = 3;
    private final static int UPG_DAMAGE_B = 0;

    private final static int BLOCK_A = 0;
    private final static int BLOCK_B = 8;
    private final static int UPG_BLOCK_A = 0;
    private final static int UPG_BLOCK_B = 3;

    public PounceTurnTail()
    {
        super(cardInfo, CardType.SKILL, CardTarget.SELF, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setBlock(BLOCK_A, BLOCK_B, UPG_BLOCK_A, UPG_BLOCK_B);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            if (p instanceof TwoFormCharacter && ((TwoFormCharacter) p).Form) {
                AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
            }

            if (m != null)
            {
                String[] sounds = { "ATTACK_WHIFF_1", "ATTACK_DAGGER_5", "ATTACK_DAGGER_6" };
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CustomVerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F, sounds, Color.BLACK, Color.RED)));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        else //cat
        {
            if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
                AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
            }

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }
}