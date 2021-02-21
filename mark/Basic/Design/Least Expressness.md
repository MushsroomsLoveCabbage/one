#### 最小表现力原则

**writing code that expresses my thinking about the problem domain**
**principle of least expressiveness (PLE)**

- use PLE to reveal my thinking about the problem domain 
- all ambiguity stops at the code, the art of programming  using the PLE can help me simplify and debug  the flawed ideas(有缺陷的想法)  i have in my head

------

least expressive model that result in a natural program
with the caveat not to contort the code 
in the rule of least power

- When programming a component, the right computation model for the component is the least expressive model that results in a natural program.
  The least expressive model means that if you can express your idea with a constant, use that, and similarly for lookup tables, state machines, and so on. You should only use a Turing-complete language when you cannot use something simpler with the caveat not to contort the code.

```java
// v1
if (file.format() == JPG || file.format() == PNG) {
  return new DecoderA().decode(file);
} else if (file.format() == GIF) {
  return new DecoderB().decode(file);
} else {
  throw new IllegalStateException(); 
}

// v2
Map<Format, Decoder> decoders=new HashMap<>();
map.add(JPG, new DecoderA());
map.add(PNG, new DecoderA());
map.add(GIF, new DecoderB());
return getOptional(decoders, file.format())
.map(Decoder::decode)
.orElseThrow(IllegalStateException::new);

// v3  this was most correct way
final Map<Format, Decoder> DECODERS = ImmutableMap.builder()
.add(JPG, new DecoderA())
.add(PNG, new DecoderA())
.add(GIF, new DecoderB())
.build();
```

#### 文献链接

- [PrincipleOfLeastExpressiveness](https://www.info.ucl.ac.be/~pvr/PrincipleOfLeastExpressiveness.pdf)

