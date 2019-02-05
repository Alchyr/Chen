package Chen.Interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface BetterOnDamageGiveSubscriber {
    float betterAtDamageGive(float dmg, DamageInfo.DamageType damageType, AbstractCreature target);
}
