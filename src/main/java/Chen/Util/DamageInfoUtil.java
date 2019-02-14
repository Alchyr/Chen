package Chen.Util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class DamageInfoUtil {

    public static void ApplyTargetPowers(DamageInfo info, AbstractCreature target)
    {
        info.output = info.base;
        info.isModified = false;
        float tmp = (float)info.output;


        for (AbstractPower p : target.powers)
        {
            tmp = p.atDamageReceive(tmp, info.type);
            if (info.base != info.output) {
                info.isModified = true;
            }
        }
        for (AbstractPower p : target.powers)
        {
            tmp = p.atDamageFinalReceive(tmp, info.type);
            if (info.base != info.output) {
                info.isModified = true;
            }
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        info.output = MathUtils.floor(tmp);
    }
}
