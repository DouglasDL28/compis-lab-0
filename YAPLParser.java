// Generated from YAPL.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class YAPLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, SELF=20, SELF_TYPE=21, IF=22, NEW=23, ISVOID=24, LET=25, 
		IN=26, WHILE=27, LOOP=28, POOL=29, ELSE=30, FI=31, THEN=32, INHERITS=33, 
		NOT=34, TRUE=35, FALSE=36, ASSIGN=37, CLASS=38, TYPE=39, OBJ_TYPE=40, 
		INT=41, COMMENT=42, LINE_COMMENT=43, WS=44;
	public static final int
		RULE_program = 0, RULE_class_gmr = 1, RULE_type = 2, RULE_feature = 3, 
		RULE_formal = 4, RULE_string = 5, RULE_expr = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "class_gmr", "type", "feature", "formal", "string", "expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "';'", "'}'", "'('", "','", "')'", "':'", "'\"'", "'\\'", 
			"'@'", "'.'", "'~'", "'*'", "'/'", "'+'", "'-'", "'<'", "'<='", "'='", 
			"'self'", "'SELF_TYPE'", null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "'true'", "'false'", "'<-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "SELF", "SELF_TYPE", 
			"IF", "NEW", "ISVOID", "LET", "IN", "WHILE", "LOOP", "POOL", "ELSE", 
			"FI", "THEN", "INHERITS", "NOT", "TRUE", "FALSE", "ASSIGN", "CLASS", 
			"TYPE", "OBJ_TYPE", "INT", "COMMENT", "LINE_COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "YAPL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public YAPLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public List<Class_gmrContext> class_gmr() {
			return getRuleContexts(Class_gmrContext.class);
		}
		public Class_gmrContext class_gmr(int i) {
			return getRuleContext(Class_gmrContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(14);
				class_gmr();
				}
				}
				setState(17); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASS );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Class_gmrContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(YAPLParser.CLASS, 0); }
		public List<TerminalNode> TYPE() { return getTokens(YAPLParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(YAPLParser.TYPE, i);
		}
		public TerminalNode INHERITS() { return getToken(YAPLParser.INHERITS, 0); }
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public Class_gmrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_class_gmr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterClass_gmr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitClass_gmr(this);
		}
	}

	public final Class_gmrContext class_gmr() throws RecognitionException {
		Class_gmrContext _localctx = new Class_gmrContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_class_gmr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			match(CLASS);
			setState(20);
			match(TYPE);
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INHERITS) {
				{
				setState(21);
				match(INHERITS);
				setState(22);
				match(TYPE);
				}
			}

			setState(25);
			match(T__0);
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OBJ_TYPE) {
				{
				{
				setState(26);
				feature();
				setState(27);
				match(T__1);
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
			match(T__2);
			setState(35);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(YAPLParser.TYPE, 0); }
		public TerminalNode SELF_TYPE() { return getToken(YAPLParser.SELF_TYPE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_la = _input.LA(1);
			if ( !(_la==SELF_TYPE || _la==TYPE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FeatureContext extends ParserRuleContext {
		public TerminalNode OBJ_TYPE() { return getToken(YAPLParser.OBJ_TYPE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<FormalContext> formal() {
			return getRuleContexts(FormalContext.class);
		}
		public FormalContext formal(int i) {
			return getRuleContext(FormalContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(YAPLParser.ASSIGN, 0); }
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterFeature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitFeature(this);
		}
	}

	public final FeatureContext feature() throws RecognitionException {
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_feature);
		int _la;
		try {
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(39);
				match(OBJ_TYPE);
				setState(40);
				match(T__3);
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OBJ_TYPE) {
					{
					setState(41);
					formal();
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__4) {
						{
						{
						setState(42);
						match(T__4);
						setState(43);
						formal();
						}
						}
						setState(48);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(51);
				match(T__5);
				setState(52);
				match(T__6);
				setState(53);
				type();
				setState(54);
				match(T__0);
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << T__7) | (1L << T__11) | (1L << SELF) | (1L << IF) | (1L << NEW) | (1L << ISVOID) | (1L << LET) | (1L << WHILE) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << OBJ_TYPE) | (1L << INT))) != 0)) {
					{
					{
					setState(55);
					expr(0);
					}
					}
					setState(60);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(61);
				match(T__2);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(63);
				match(OBJ_TYPE);
				setState(64);
				match(T__6);
				setState(65);
				type();
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(66);
					match(ASSIGN);
					setState(67);
					expr(0);
					}
				}

				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalContext extends ParserRuleContext {
		public TerminalNode OBJ_TYPE() { return getToken(YAPLParser.OBJ_TYPE, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterFormal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitFormal(this);
		}
	}

	public final FormalContext formal() throws RecognitionException {
		FormalContext _localctx = new FormalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_formal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(OBJ_TYPE);
			setState(73);
			match(T__6);
			setState(74);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public List<TerminalNode> EOF() { return getTokens(YAPLParser.EOF); }
		public TerminalNode EOF(int i) {
			return getToken(YAPLParser.EOF, i);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(T__7);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << SELF) | (1L << SELF_TYPE) | (1L << IF) | (1L << NEW) | (1L << ISVOID) | (1L << LET) | (1L << IN) | (1L << WHILE) | (1L << LOOP) | (1L << POOL) | (1L << ELSE) | (1L << FI) | (1L << THEN) | (1L << INHERITS) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << ASSIGN) | (1L << CLASS) | (1L << TYPE) | (1L << OBJ_TYPE) | (1L << INT) | (1L << COMMENT) | (1L << LINE_COMMENT) | (1L << WS))) != 0)) {
				{
				setState(79);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
				case 1:
					{
					setState(77);
					_la = _input.LA(1);
					if ( _la <= 0 || (_la==EOF || _la==T__7) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				case 2:
					{
					setState(78);
					match(T__8);
					}
					break;
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<TerminalNode> OBJ_TYPE() { return getTokens(YAPLParser.OBJ_TYPE); }
		public TerminalNode OBJ_TYPE(int i) {
			return getToken(YAPLParser.OBJ_TYPE, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IF() { return getToken(YAPLParser.IF, 0); }
		public TerminalNode THEN() { return getToken(YAPLParser.THEN, 0); }
		public TerminalNode ELSE() { return getToken(YAPLParser.ELSE, 0); }
		public TerminalNode FI() { return getToken(YAPLParser.FI, 0); }
		public TerminalNode WHILE() { return getToken(YAPLParser.WHILE, 0); }
		public TerminalNode LOOP() { return getToken(YAPLParser.LOOP, 0); }
		public TerminalNode POOL() { return getToken(YAPLParser.POOL, 0); }
		public TerminalNode LET() { return getToken(YAPLParser.LET, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TerminalNode IN() { return getToken(YAPLParser.IN, 0); }
		public List<TerminalNode> ASSIGN() { return getTokens(YAPLParser.ASSIGN); }
		public TerminalNode ASSIGN(int i) {
			return getToken(YAPLParser.ASSIGN, i);
		}
		public TerminalNode NEW() { return getToken(YAPLParser.NEW, 0); }
		public TerminalNode ISVOID() { return getToken(YAPLParser.ISVOID, 0); }
		public TerminalNode NOT() { return getToken(YAPLParser.NOT, 0); }
		public TerminalNode INT() { return getToken(YAPLParser.INT, 0); }
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode TRUE() { return getToken(YAPLParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(YAPLParser.FALSE, 0); }
		public TerminalNode SELF() { return getToken(YAPLParser.SELF, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof YAPLListener ) ((YAPLListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(87);
				match(OBJ_TYPE);
				setState(88);
				match(T__3);
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << T__7) | (1L << T__11) | (1L << SELF) | (1L << IF) | (1L << NEW) | (1L << ISVOID) | (1L << LET) | (1L << WHILE) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << OBJ_TYPE) | (1L << INT))) != 0)) {
					{
					setState(89);
					expr(0);
					setState(94);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__4) {
						{
						{
						setState(90);
						match(T__4);
						setState(91);
						expr(0);
						}
						}
						setState(96);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(99);
				match(T__5);
				}
				break;
			case 2:
				{
				setState(100);
				match(IF);
				setState(101);
				expr(0);
				setState(102);
				match(THEN);
				setState(103);
				expr(0);
				setState(104);
				match(ELSE);
				setState(105);
				expr(0);
				setState(106);
				match(FI);
				}
				break;
			case 3:
				{
				setState(108);
				match(WHILE);
				setState(109);
				expr(0);
				setState(110);
				match(LOOP);
				setState(111);
				expr(0);
				setState(112);
				match(POOL);
				}
				break;
			case 4:
				{
				setState(114);
				match(T__0);
				setState(118); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(115);
					expr(0);
					setState(116);
					match(T__1);
					}
					}
					setState(120); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << T__7) | (1L << T__11) | (1L << SELF) | (1L << IF) | (1L << NEW) | (1L << ISVOID) | (1L << LET) | (1L << WHILE) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << OBJ_TYPE) | (1L << INT))) != 0) );
				setState(122);
				match(T__2);
				}
				break;
			case 5:
				{
				setState(124);
				match(LET);
				setState(125);
				match(OBJ_TYPE);
				setState(126);
				match(T__6);
				setState(127);
				type();
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ASSIGN) {
					{
					setState(128);
					match(ASSIGN);
					setState(129);
					expr(0);
					}
				}

				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(132);
					match(T__4);
					setState(133);
					match(OBJ_TYPE);
					setState(134);
					match(T__6);
					setState(135);
					type();
					setState(138);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ASSIGN) {
						{
						setState(136);
						match(ASSIGN);
						setState(137);
						expr(0);
						}
					}

					}
					}
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(145);
				match(IN);
				setState(146);
				expr(20);
				}
				break;
			case 6:
				{
				setState(148);
				match(NEW);
				setState(149);
				type();
				}
				break;
			case 7:
				{
				setState(150);
				match(T__11);
				setState(151);
				expr(18);
				}
				break;
			case 8:
				{
				setState(152);
				match(ISVOID);
				setState(153);
				expr(17);
				}
				break;
			case 9:
				{
				setState(154);
				match(NOT);
				setState(155);
				expr(9);
				}
				break;
			case 10:
				{
				setState(156);
				match(OBJ_TYPE);
				setState(157);
				match(ASSIGN);
				setState(158);
				expr(8);
				}
				break;
			case 11:
				{
				setState(159);
				match(T__3);
				setState(160);
				expr(0);
				setState(161);
				match(T__5);
				}
				break;
			case 12:
				{
				setState(163);
				match(OBJ_TYPE);
				}
				break;
			case 13:
				{
				setState(164);
				match(INT);
				}
				break;
			case 14:
				{
				setState(165);
				string();
				}
				break;
			case 15:
				{
				setState(166);
				match(TRUE);
				}
				break;
			case 16:
				{
				setState(167);
				match(FALSE);
				}
				break;
			case 17:
				{
				setState(168);
				match(SELF);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(213);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(211);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(171);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(172);
						match(T__12);
						setState(173);
						expr(17);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(174);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(175);
						match(T__13);
						setState(176);
						expr(16);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(177);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(178);
						match(T__14);
						setState(179);
						expr(15);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(180);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(181);
						match(T__15);
						setState(182);
						expr(14);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(183);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(184);
						match(T__16);
						setState(185);
						expr(13);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(186);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(187);
						match(T__17);
						setState(188);
						expr(12);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(189);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(190);
						match(T__18);
						setState(191);
						expr(11);
						}
						break;
					case 8:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(192);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(195);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==T__9) {
							{
							setState(193);
							match(T__9);
							setState(194);
							type();
							}
						}

						setState(197);
						match(T__10);
						setState(198);
						match(OBJ_TYPE);
						setState(199);
						match(T__3);
						setState(208);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__3) | (1L << T__7) | (1L << T__11) | (1L << SELF) | (1L << IF) | (1L << NEW) | (1L << ISVOID) | (1L << LET) | (1L << WHILE) | (1L << NOT) | (1L << TRUE) | (1L << FALSE) | (1L << OBJ_TYPE) | (1L << INT))) != 0)) {
							{
							setState(200);
							expr(0);
							setState(205);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__4) {
								{
								{
								setState(201);
								match(T__4);
								setState(202);
								expr(0);
								}
								}
								setState(207);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(210);
						match(T__5);
						}
						break;
					}
					} 
				}
				setState(215);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 25);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001,\u00d9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0004\u0000\u0010"+
		"\b\u0000\u000b\u0000\f\u0000\u0011\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0003\u0001\u0018\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0005\u0001\u001e\b\u0001\n\u0001\f\u0001!\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003-\b\u0003\n\u0003"+
		"\f\u00030\t\u0003\u0003\u00032\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u00039\b\u0003\n\u0003\f\u0003<\t\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003E\b\u0003\u0003\u0003G\b\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0005"+
		"\u0005P\b\u0005\n\u0005\f\u0005S\t\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005"+
		"\u0006]\b\u0006\n\u0006\f\u0006`\t\u0006\u0003\u0006b\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0004\u0006w\b\u0006\u000b\u0006\f\u0006x\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u0083\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006\u008b\b\u0006\u0005\u0006\u008d\b\u0006"+
		"\n\u0006\f\u0006\u0090\t\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00aa\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u00c4"+
		"\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u0006\u00cc\b\u0006\n\u0006\f\u0006\u00cf\t\u0006\u0003\u0006"+
		"\u00d1\b\u0006\u0001\u0006\u0005\u0006\u00d4\b\u0006\n\u0006\f\u0006\u00d7"+
		"\t\u0006\u0001\u0006\u0000\u0001\f\u0007\u0000\u0002\u0004\u0006\b\n\f"+
		"\u0000\u0002\u0002\u0000\u0015\u0015\'\'\u0001\u0001\b\b\u00fc\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0002\u0013\u0001\u0000\u0000\u0000\u0004%\u0001"+
		"\u0000\u0000\u0000\u0006F\u0001\u0000\u0000\u0000\bH\u0001\u0000\u0000"+
		"\u0000\nL\u0001\u0000\u0000\u0000\f\u00a9\u0001\u0000\u0000\u0000\u000e"+
		"\u0010\u0003\u0002\u0001\u0000\u000f\u000e\u0001\u0000\u0000\u0000\u0010"+
		"\u0011\u0001\u0000\u0000\u0000\u0011\u000f\u0001\u0000\u0000\u0000\u0011"+
		"\u0012\u0001\u0000\u0000\u0000\u0012\u0001\u0001\u0000\u0000\u0000\u0013"+
		"\u0014\u0005&\u0000\u0000\u0014\u0017\u0005\'\u0000\u0000\u0015\u0016"+
		"\u0005!\u0000\u0000\u0016\u0018\u0005\'\u0000\u0000\u0017\u0015\u0001"+
		"\u0000\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000\u0018\u0019\u0001"+
		"\u0000\u0000\u0000\u0019\u001f\u0005\u0001\u0000\u0000\u001a\u001b\u0003"+
		"\u0006\u0003\u0000\u001b\u001c\u0005\u0002\u0000\u0000\u001c\u001e\u0001"+
		"\u0000\u0000\u0000\u001d\u001a\u0001\u0000\u0000\u0000\u001e!\u0001\u0000"+
		"\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000"+
		"\u0000 \"\u0001\u0000\u0000\u0000!\u001f\u0001\u0000\u0000\u0000\"#\u0005"+
		"\u0003\u0000\u0000#$\u0005\u0002\u0000\u0000$\u0003\u0001\u0000\u0000"+
		"\u0000%&\u0007\u0000\u0000\u0000&\u0005\u0001\u0000\u0000\u0000\'(\u0005"+
		"(\u0000\u0000(1\u0005\u0004\u0000\u0000).\u0003\b\u0004\u0000*+\u0005"+
		"\u0005\u0000\u0000+-\u0003\b\u0004\u0000,*\u0001\u0000\u0000\u0000-0\u0001"+
		"\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000"+
		"/2\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u00001)\u0001\u0000\u0000"+
		"\u000012\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000034\u0005\u0006"+
		"\u0000\u000045\u0005\u0007\u0000\u000056\u0003\u0004\u0002\u00006:\u0005"+
		"\u0001\u0000\u000079\u0003\f\u0006\u000087\u0001\u0000\u0000\u00009<\u0001"+
		"\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000"+
		";=\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000=>\u0005\u0003\u0000"+
		"\u0000>G\u0001\u0000\u0000\u0000?@\u0005(\u0000\u0000@A\u0005\u0007\u0000"+
		"\u0000AD\u0003\u0004\u0002\u0000BC\u0005%\u0000\u0000CE\u0003\f\u0006"+
		"\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000EG\u0001\u0000"+
		"\u0000\u0000F\'\u0001\u0000\u0000\u0000F?\u0001\u0000\u0000\u0000G\u0007"+
		"\u0001\u0000\u0000\u0000HI\u0005(\u0000\u0000IJ\u0005\u0007\u0000\u0000"+
		"JK\u0003\u0004\u0002\u0000K\t\u0001\u0000\u0000\u0000LQ\u0005\b\u0000"+
		"\u0000MP\b\u0001\u0000\u0000NP\u0005\t\u0000\u0000OM\u0001\u0000\u0000"+
		"\u0000ON\u0001\u0000\u0000\u0000PS\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000QR\u0001\u0000\u0000\u0000RT\u0001\u0000\u0000\u0000SQ\u0001"+
		"\u0000\u0000\u0000TU\u0005\b\u0000\u0000U\u000b\u0001\u0000\u0000\u0000"+
		"VW\u0006\u0006\uffff\uffff\u0000WX\u0005(\u0000\u0000Xa\u0005\u0004\u0000"+
		"\u0000Y^\u0003\f\u0006\u0000Z[\u0005\u0005\u0000\u0000[]\u0003\f\u0006"+
		"\u0000\\Z\u0001\u0000\u0000\u0000]`\u0001\u0000\u0000\u0000^\\\u0001\u0000"+
		"\u0000\u0000^_\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001"+
		"\u0000\u0000\u0000aY\u0001\u0000\u0000\u0000ab\u0001\u0000\u0000\u0000"+
		"bc\u0001\u0000\u0000\u0000c\u00aa\u0005\u0006\u0000\u0000de\u0005\u0016"+
		"\u0000\u0000ef\u0003\f\u0006\u0000fg\u0005 \u0000\u0000gh\u0003\f\u0006"+
		"\u0000hi\u0005\u001e\u0000\u0000ij\u0003\f\u0006\u0000jk\u0005\u001f\u0000"+
		"\u0000k\u00aa\u0001\u0000\u0000\u0000lm\u0005\u001b\u0000\u0000mn\u0003"+
		"\f\u0006\u0000no\u0005\u001c\u0000\u0000op\u0003\f\u0006\u0000pq\u0005"+
		"\u001d\u0000\u0000q\u00aa\u0001\u0000\u0000\u0000rv\u0005\u0001\u0000"+
		"\u0000st\u0003\f\u0006\u0000tu\u0005\u0002\u0000\u0000uw\u0001\u0000\u0000"+
		"\u0000vs\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000xv\u0001\u0000"+
		"\u0000\u0000xy\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z{\u0005"+
		"\u0003\u0000\u0000{\u00aa\u0001\u0000\u0000\u0000|}\u0005\u0019\u0000"+
		"\u0000}~\u0005(\u0000\u0000~\u007f\u0005\u0007\u0000\u0000\u007f\u0082"+
		"\u0003\u0004\u0002\u0000\u0080\u0081\u0005%\u0000\u0000\u0081\u0083\u0003"+
		"\f\u0006\u0000\u0082\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000"+
		"\u0000\u0000\u0083\u008e\u0001\u0000\u0000\u0000\u0084\u0085\u0005\u0005"+
		"\u0000\u0000\u0085\u0086\u0005(\u0000\u0000\u0086\u0087\u0005\u0007\u0000"+
		"\u0000\u0087\u008a\u0003\u0004\u0002\u0000\u0088\u0089\u0005%\u0000\u0000"+
		"\u0089\u008b\u0003\f\u0006\u0000\u008a\u0088\u0001\u0000\u0000\u0000\u008a"+
		"\u008b\u0001\u0000\u0000\u0000\u008b\u008d\u0001\u0000\u0000\u0000\u008c"+
		"\u0084\u0001\u0000\u0000\u0000\u008d\u0090\u0001\u0000\u0000\u0000\u008e"+
		"\u008c\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f"+
		"\u0091\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0091"+
		"\u0092\u0005\u001a\u0000\u0000\u0092\u0093\u0003\f\u0006\u0014\u0093\u00aa"+
		"\u0001\u0000\u0000\u0000\u0094\u0095\u0005\u0017\u0000\u0000\u0095\u00aa"+
		"\u0003\u0004\u0002\u0000\u0096\u0097\u0005\f\u0000\u0000\u0097\u00aa\u0003"+
		"\f\u0006\u0012\u0098\u0099\u0005\u0018\u0000\u0000\u0099\u00aa\u0003\f"+
		"\u0006\u0011\u009a\u009b\u0005\"\u0000\u0000\u009b\u00aa\u0003\f\u0006"+
		"\t\u009c\u009d\u0005(\u0000\u0000\u009d\u009e\u0005%\u0000\u0000\u009e"+
		"\u00aa\u0003\f\u0006\b\u009f\u00a0\u0005\u0004\u0000\u0000\u00a0\u00a1"+
		"\u0003\f\u0006\u0000\u00a1\u00a2\u0005\u0006\u0000\u0000\u00a2\u00aa\u0001"+
		"\u0000\u0000\u0000\u00a3\u00aa\u0005(\u0000\u0000\u00a4\u00aa\u0005)\u0000"+
		"\u0000\u00a5\u00aa\u0003\n\u0005\u0000\u00a6\u00aa\u0005#\u0000\u0000"+
		"\u00a7\u00aa\u0005$\u0000\u0000\u00a8\u00aa\u0005\u0014\u0000\u0000\u00a9"+
		"V\u0001\u0000\u0000\u0000\u00a9d\u0001\u0000\u0000\u0000\u00a9l\u0001"+
		"\u0000\u0000\u0000\u00a9r\u0001\u0000\u0000\u0000\u00a9|\u0001\u0000\u0000"+
		"\u0000\u00a9\u0094\u0001\u0000\u0000\u0000\u00a9\u0096\u0001\u0000\u0000"+
		"\u0000\u00a9\u0098\u0001\u0000\u0000\u0000\u00a9\u009a\u0001\u0000\u0000"+
		"\u0000\u00a9\u009c\u0001\u0000\u0000\u0000\u00a9\u009f\u0001\u0000\u0000"+
		"\u0000\u00a9\u00a3\u0001\u0000\u0000\u0000\u00a9\u00a4\u0001\u0000\u0000"+
		"\u0000\u00a9\u00a5\u0001\u0000\u0000\u0000\u00a9\u00a6\u0001\u0000\u0000"+
		"\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00a9\u00a8\u0001\u0000\u0000"+
		"\u0000\u00aa\u00d5\u0001\u0000\u0000\u0000\u00ab\u00ac\n\u0010\u0000\u0000"+
		"\u00ac\u00ad\u0005\r\u0000\u0000\u00ad\u00d4\u0003\f\u0006\u0011\u00ae"+
		"\u00af\n\u000f\u0000\u0000\u00af\u00b0\u0005\u000e\u0000\u0000\u00b0\u00d4"+
		"\u0003\f\u0006\u0010\u00b1\u00b2\n\u000e\u0000\u0000\u00b2\u00b3\u0005"+
		"\u000f\u0000\u0000\u00b3\u00d4\u0003\f\u0006\u000f\u00b4\u00b5\n\r\u0000"+
		"\u0000\u00b5\u00b6\u0005\u0010\u0000\u0000\u00b6\u00d4\u0003\f\u0006\u000e"+
		"\u00b7\u00b8\n\f\u0000\u0000\u00b8\u00b9\u0005\u0011\u0000\u0000\u00b9"+
		"\u00d4\u0003\f\u0006\r\u00ba\u00bb\n\u000b\u0000\u0000\u00bb\u00bc\u0005"+
		"\u0012\u0000\u0000\u00bc\u00d4\u0003\f\u0006\f\u00bd\u00be\n\n\u0000\u0000"+
		"\u00be\u00bf\u0005\u0013\u0000\u0000\u00bf\u00d4\u0003\f\u0006\u000b\u00c0"+
		"\u00c3\n\u0019\u0000\u0000\u00c1\u00c2\u0005\n\u0000\u0000\u00c2\u00c4"+
		"\u0003\u0004\u0002\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c4"+
		"\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000\u00c5\u00c6"+
		"\u0005\u000b\u0000\u0000\u00c6\u00c7\u0005(\u0000\u0000\u00c7\u00d0\u0005"+
		"\u0004\u0000\u0000\u00c8\u00cd\u0003\f\u0006\u0000\u00c9\u00ca\u0005\u0005"+
		"\u0000\u0000\u00ca\u00cc\u0003\f\u0006\u0000\u00cb\u00c9\u0001\u0000\u0000"+
		"\u0000\u00cc\u00cf\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000"+
		"\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000\u00ce\u00d1\u0001\u0000\u0000"+
		"\u0000\u00cf\u00cd\u0001\u0000\u0000\u0000\u00d0\u00c8\u0001\u0000\u0000"+
		"\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000\u0000"+
		"\u0000\u00d2\u00d4\u0005\u0006\u0000\u0000\u00d3\u00ab\u0001\u0000\u0000"+
		"\u0000\u00d3\u00ae\u0001\u0000\u0000\u0000\u00d3\u00b1\u0001\u0000\u0000"+
		"\u0000\u00d3\u00b4\u0001\u0000\u0000\u0000\u00d3\u00b7\u0001\u0000\u0000"+
		"\u0000\u00d3\u00ba\u0001\u0000\u0000\u0000\u00d3\u00bd\u0001\u0000\u0000"+
		"\u0000\u00d3\u00c0\u0001\u0000\u0000\u0000\u00d4\u00d7\u0001\u0000\u0000"+
		"\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d5\u00d6\u0001\u0000\u0000"+
		"\u0000\u00d6\r\u0001\u0000\u0000\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000"+
		"\u0016\u0011\u0017\u001f.1:DFOQ^ax\u0082\u008a\u008e\u00a9\u00c3\u00cd"+
		"\u00d0\u00d3\u00d5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}