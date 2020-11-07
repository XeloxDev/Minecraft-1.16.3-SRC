package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompoundNBT implements INBT
{
    public static final Codec<CompoundNBT> field_240597_a_ = Codec.PASSTHROUGH.comapFlatMap((p_240598_0_) ->
    {
        INBT inbt = p_240598_0_.convert(NBTDynamicOps.INSTANCE).getValue();
        return inbt instanceof CompoundNBT ? DataResult.success((CompoundNBT)inbt) : DataResult.error("Not a compound tag: " + inbt);
    }, (p_240599_0_) ->
    {
        return new Dynamic<>(NBTDynamicOps.INSTANCE, p_240599_0_);
    });
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");
    public static final INBTType<CompoundNBT> TYPE = new INBTType<CompoundNBT>()
    {
        public CompoundNBT func_225649_b_(DataInput p_225649_1_, int p_225649_2_, NBTSizeTracker p_225649_3_) throws IOException
        {
            p_225649_3_.read(384L);

            if (p_225649_2_ > 512)
            {
                throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
            }
            else
            {
                Map<String, INBT> map = Maps.newHashMap();
                byte b0;

                while ((b0 = CompoundNBT.readType(p_225649_1_, p_225649_3_)) != 0)
                {
                    String s = CompoundNBT.readKey(p_225649_1_, p_225649_3_);
                    p_225649_3_.read((long)(224 + 16 * s.length()));
                    INBT inbt = CompoundNBT.func_229680_b_(NBTTypes.getGetTypeByID(b0), s, p_225649_1_, p_225649_2_ + 1, p_225649_3_);

                    if (map.put(s, inbt) != null)
                    {
                        p_225649_3_.read(288L);
                    }
                }

                return new CompoundNBT(map);
            }
        }
        public String func_225648_a_()
        {
            return "COMPOUND";
        }
        public String func_225650_b_()
        {
            return "TAG_Compound";
        }
    };
    private final Map<String, INBT> tagMap;

    protected CompoundNBT(Map<String, INBT> p_i226075_1_)
    {
        this.tagMap = p_i226075_1_;
    }

    public CompoundNBT()
    {
        this(Maps.newHashMap());
    }

    public void write(DataOutput output) throws IOException
    {
        for (String s : this.tagMap.keySet())
        {
            INBT inbt = this.tagMap.get(s);
            writeEntry(s, inbt, output);
        }

        output.writeByte(0);
    }

    public Set<String> keySet()
    {
        return this.tagMap.keySet();
    }

    public byte getId()
    {
        return 10;
    }

    public INBTType<CompoundNBT> getType()
    {
        return TYPE;
    }

    public int size()
    {
        return this.tagMap.size();
    }

    @Nullable
    public INBT put(String key, INBT value)
    {
        return this.tagMap.put(key, value);
    }

    public void putByte(String key, byte value)
    {
        this.tagMap.put(key, ByteNBT.valueOf(value));
    }

    public void putShort(String key, short value)
    {
        this.tagMap.put(key, ShortNBT.valueOf(value));
    }

    public void putInt(String key, int value)
    {
        this.tagMap.put(key, IntNBT.valueOf(value));
    }

    public void putLong(String key, long value)
    {
        this.tagMap.put(key, LongNBT.valueOf(value));
    }

    public void putUniqueId(String key, UUID value)
    {
        this.tagMap.put(key, NBTUtil.func_240626_a_(value));
    }

    public UUID getUniqueId(String key)
    {
        return NBTUtil.readUniqueId(this.get(key));
    }

    public boolean hasUniqueId(String key)
    {
        INBT inbt = this.get(key);
        return inbt != null && inbt.getType() == IntArrayNBT.TYPE && ((IntArrayNBT)inbt).getIntArray().length == 4;
    }

    public void putFloat(String key, float value)
    {
        this.tagMap.put(key, FloatNBT.valueOf(value));
    }

    public void putDouble(String key, double value)
    {
        this.tagMap.put(key, DoubleNBT.valueOf(value));
    }

    public void putString(String key, String value)
    {
        this.tagMap.put(key, StringNBT.valueOf(value));
    }

    public void putByteArray(String key, byte[] value)
    {
        this.tagMap.put(key, new ByteArrayNBT(value));
    }

    public void putIntArray(String key, int[] value)
    {
        this.tagMap.put(key, new IntArrayNBT(value));
    }

    public void putIntArray(String key, List<Integer> value)
    {
        this.tagMap.put(key, new IntArrayNBT(value));
    }

    public void putLongArray(String key, long[] value)
    {
        this.tagMap.put(key, new LongArrayNBT(value));
    }

    public void putLongArray(String key, List<Long> value)
    {
        this.tagMap.put(key, new LongArrayNBT(value));
    }

    public void putBoolean(String key, boolean value)
    {
        this.tagMap.put(key, ByteNBT.valueOf(value));
    }

    @Nullable
    public INBT get(String key)
    {
        return this.tagMap.get(key);
    }

    public byte getTagId(String key)
    {
        INBT inbt = this.tagMap.get(key);
        return inbt == null ? 0 : inbt.getId();
    }

    public boolean contains(String key)
    {
        return this.tagMap.containsKey(key);
    }

    public boolean contains(String key, int type)
    {
        int i = this.getTagId(key);

        if (i == type)
        {
            return true;
        }
        else if (type != 99)
        {
            return false;
        }
        else
        {
            return i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6;
        }
    }

    public byte getByte(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getByte();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0;
    }

    public short getShort(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getShort();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0;
    }

    public int getInt(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getInt();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0;
    }

    public long getLong(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getLong();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0L;
    }

    public float getFloat(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getFloat();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0.0F;
    }

    public double getDouble(String key)
    {
        try
        {
            if (this.contains(key, 99))
            {
                return ((NumberNBT)this.tagMap.get(key)).getDouble();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return 0.0D;
    }

    public String getString(String key)
    {
        try
        {
            if (this.contains(key, 8))
            {
                return this.tagMap.get(key).getString();
            }
        }
        catch (ClassCastException classcastexception)
        {
        }

        return "";
    }

    public byte[] getByteArray(String key)
    {
        try
        {
            if (this.contains(key, 7))
            {
                return ((ByteArrayNBT)this.tagMap.get(key)).getByteArray();
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.func_229677_a_(key, ByteArrayNBT.TYPE, classcastexception));
        }

        return new byte[0];
    }

    public int[] getIntArray(String key)
    {
        try
        {
            if (this.contains(key, 11))
            {
                return ((IntArrayNBT)this.tagMap.get(key)).getIntArray();
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.func_229677_a_(key, IntArrayNBT.TYPE, classcastexception));
        }

        return new int[0];
    }

    public long[] getLongArray(String key)
    {
        try
        {
            if (this.contains(key, 12))
            {
                return ((LongArrayNBT)this.tagMap.get(key)).getAsLongArray();
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.func_229677_a_(key, LongArrayNBT.TYPE, classcastexception));
        }

        return new long[0];
    }

    public CompoundNBT getCompound(String key)
    {
        try
        {
            if (this.contains(key, 10))
            {
                return (CompoundNBT)this.tagMap.get(key);
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.func_229677_a_(key, TYPE, classcastexception));
        }

        return new CompoundNBT();
    }

    public ListNBT getList(String key, int type)
    {
        try
        {
            if (this.getTagId(key) == 9)
            {
                ListNBT listnbt = (ListNBT)this.tagMap.get(key);

                if (!listnbt.isEmpty() && listnbt.func_230528_d__() != type)
                {
                    return new ListNBT();
                }

                return listnbt;
            }
        }
        catch (ClassCastException classcastexception)
        {
            throw new ReportedException(this.func_229677_a_(key, ListNBT.TYPE, classcastexception));
        }

        return new ListNBT();
    }

    public boolean getBoolean(String key)
    {
        return this.getByte(key) != 0;
    }

    public void remove(String key)
    {
        this.tagMap.remove(key);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder("{");
        Collection<String> collection = this.tagMap.keySet();

        if (LOGGER.isDebugEnabled())
        {
            List<String> list = Lists.newArrayList(this.tagMap.keySet());
            Collections.sort(list);
            collection = list;
        }

        for (String s : collection)
        {
            if (stringbuilder.length() != 1)
            {
                stringbuilder.append(',');
            }

            stringbuilder.append(handleEscape(s)).append(':').append(this.tagMap.get(s));
        }

        return stringbuilder.append('}').toString();
    }

    public boolean isEmpty()
    {
        return this.tagMap.isEmpty();
    }

    private CrashReport func_229677_a_(String p_229677_1_, INBTType<?> p_229677_2_, ClassCastException p_229677_3_)
    {
        CrashReport crashreport = CrashReport.makeCrashReport(p_229677_3_, "Reading NBT data");
        CrashReportCategory crashreportcategory = crashreport.makeCategoryDepth("Corrupt NBT tag", 1);
        crashreportcategory.addDetail("Tag type found", () ->
        {
            return this.tagMap.get(p_229677_1_).getType().func_225648_a_();
        });
        crashreportcategory.addDetail("Tag type expected", p_229677_2_::func_225648_a_);
        crashreportcategory.addDetail("Tag name", p_229677_1_);
        return crashreport;
    }

    public CompoundNBT copy()
    {
        Map<String, INBT> map = Maps.newHashMap(Maps.transformValues(this.tagMap, INBT::copy));
        return new CompoundNBT(map);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else
        {
            return p_equals_1_ instanceof CompoundNBT && Objects.equals(this.tagMap, ((CompoundNBT)p_equals_1_).tagMap);
        }
    }

    public int hashCode()
    {
        return this.tagMap.hashCode();
    }

    private static void writeEntry(String name, INBT data, DataOutput output) throws IOException
    {
        output.writeByte(data.getId());

        if (data.getId() != 0)
        {
            output.writeUTF(name);
            data.write(output);
        }
    }

    private static byte readType(DataInput input, NBTSizeTracker sizeTracker) throws IOException
    {
        return input.readByte();
    }

    private static String readKey(DataInput input, NBTSizeTracker sizeTracker) throws IOException
    {
        return input.readUTF();
    }

    private static INBT func_229680_b_(INBTType<?> p_229680_0_, String p_229680_1_, DataInput p_229680_2_, int p_229680_3_, NBTSizeTracker p_229680_4_)
    {
        try
        {
            return p_229680_0_.func_225649_b_(p_229680_2_, p_229680_3_, p_229680_4_);
        }
        catch (IOException ioexception)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
            crashreportcategory.addDetail("Tag name", p_229680_1_);
            crashreportcategory.addDetail("Tag type", p_229680_0_.func_225648_a_());
            throw new ReportedException(crashreport);
        }
    }

    public CompoundNBT merge(CompoundNBT other)
    {
        for (String s : other.tagMap.keySet())
        {
            INBT inbt = other.tagMap.get(s);

            if (inbt.getId() == 10)
            {
                if (this.contains(s, 10))
                {
                    CompoundNBT compoundnbt = this.getCompound(s);
                    compoundnbt.merge((CompoundNBT)inbt);
                }
                else
                {
                    this.put(s, inbt.copy());
                }
            }
            else
            {
                this.put(s, inbt.copy());
            }
        }

        return this;
    }

    protected static String handleEscape(String p_193582_0_)
    {
        return SIMPLE_VALUE.matcher(p_193582_0_).matches() ? p_193582_0_ : StringNBT.quoteAndEscape(p_193582_0_);
    }

    protected static ITextComponent func_197642_t(String p_197642_0_)
    {
        if (SIMPLE_VALUE.matcher(p_197642_0_).matches())
        {
            return (new StringTextComponent(p_197642_0_)).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
        }
        else
        {
            String s = StringNBT.quoteAndEscape(p_197642_0_);
            String s1 = s.substring(0, 1);
            ITextComponent itextcomponent = (new StringTextComponent(s.substring(1, s.length() - 1))).mergeStyle(SYNTAX_HIGHLIGHTING_KEY);
            return (new StringTextComponent(s1)).append(itextcomponent).appendString(s1);
        }
    }

    public ITextComponent toFormattedComponent(String indentation, int indentDepth)
    {
        if (this.tagMap.isEmpty())
        {
            return new StringTextComponent("{}");
        }
        else
        {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");
            Collection<String> collection = this.tagMap.keySet();

            if (LOGGER.isDebugEnabled())
            {
                List<String> list = Lists.newArrayList(this.tagMap.keySet());
                Collections.sort(list);
                collection = list;
            }

            if (!indentation.isEmpty())
            {
                iformattabletextcomponent.appendString("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;

            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.append(iformattabletextcomponent1))
            {
                String s = iterator.next();
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(indentation, indentDepth + 1))).append(func_197642_t(s)).appendString(String.valueOf(':')).appendString(" ").append(this.tagMap.get(s).toFormattedComponent(indentation, indentDepth + 1));

                if (iterator.hasNext())
                {
                    iformattabletextcomponent1.appendString(String.valueOf(',')).appendString(indentation.isEmpty() ? " " : "\n");
                }
            }

            if (!indentation.isEmpty())
            {
                iformattabletextcomponent.appendString("\n").appendString(Strings.repeat(indentation, indentDepth));
            }

            iformattabletextcomponent.appendString("}");
            return iformattabletextcomponent;
        }
    }

    protected Map<String, INBT> func_240600_h_()
    {
        return Collections.unmodifiableMap(this.tagMap);
    }
}
