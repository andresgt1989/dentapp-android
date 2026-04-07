package com.dentapp.app.data.model;

import kotlinx.serialization.SerialName;
import kotlinx.serialization.Serializable;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\bb\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 \u0090\u00012\u00020\u0001:\u0004\u008f\u0001\u0090\u0001B\u00f3\u0002\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u000f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u0010\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u0013\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u0014\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u0017\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\u001a\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0001\u0010\u001c\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u001d\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u001e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u001f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010 \u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010!\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010\"\u001a\u0004\u0018\u00010\n\u0012\n\b\u0001\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010$\u001a\u0004\u0018\u00010%\u00a2\u0006\u0002\u0010&B\u00ed\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\'J\u000b\u0010c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010d\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010e\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010f\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010g\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010h\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010i\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010j\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010k\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010l\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010m\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010n\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010o\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010p\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010q\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u00102J\u000b\u0010r\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010s\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010u\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010v\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010w\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u0010\u0010x\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010y\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010z\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u00102J\u0010\u0010{\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u00102J\u0010\u0010|\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010}\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010~\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u00106J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\f\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u00f8\u0002\u0010\u0081\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0003\u0010\u0082\u0001J\u0015\u0010\u0083\u0001\u001a\u00020\n2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0003H\u00d6\u0001J\n\u0010\u0086\u0001\u001a\u00020\u0005H\u00d6\u0001J.\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u0089\u0001\u001a\u00020\u00002\b\u0010\u008a\u0001\u001a\u00030\u008b\u00012\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001H\u00c1\u0001\u00a2\u0006\u0003\b\u008e\u0001R\u001e\u0010\u001c\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\u001e\u0010\r\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b,\u0010)\u001a\u0004\b-\u0010+R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b.\u0010)\u001a\u0004\b/\u0010+R \u0010\u001b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00103\u0012\u0004\b0\u0010)\u001a\u0004\b1\u00102R \u0010\t\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\b4\u0010)\u001a\u0004\b5\u00106R\u001e\u0010\u000b\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b8\u0010)\u001a\u0004\b9\u0010+R \u0010\u001f\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\b:\u0010)\u001a\u0004\b;\u00106R \u0010\u0017\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\b<\u0010)\u001a\u0004\b=\u00106R\u0015\u0010\u0016\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00107\u001a\u0004\b>\u00106R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00107\u001a\u0004\b?\u00106R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00107\u001a\u0004\b@\u00106R \u0010\u0010\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bA\u0010)\u001a\u0004\bB\u00106R \u0010\u0013\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bC\u0010)\u001a\u0004\bD\u00106R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010+R \u0010\"\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bF\u0010)\u001a\u0004\bG\u00106R \u0010\u0014\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bH\u0010)\u001a\u0004\bI\u00106R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bJ\u0010)\u001a\u0004\bK\u0010+R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00107\u001a\u0004\bL\u00106R\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bM\u0010)\u001a\u0004\bN\u0010+R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bO\u0010)\u001a\u0004\bP\u0010+R \u0010#\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00103\u0012\u0004\bQ\u0010)\u001a\u0004\bR\u00102R \u0010\u000f\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bS\u0010)\u001a\u0004\bT\u00106R\u0015\u0010\u0019\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u00107\u001a\u0004\bU\u00106R\u001e\u0010\u001a\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bV\u0010)\u001a\u0004\bW\u0010+R \u0010\f\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\bX\u0010)\u001a\u0004\bY\u00106R\u001e\u0010\u001e\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\bZ\u0010)\u001a\u0004\b[\u0010+R\u001e\u0010\u001d\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\\\u0010)\u001a\u0004\b]\u0010+R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u00103\u001a\u0004\b^\u00102R \u0010 \u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\b_\u0010)\u001a\u0004\b`\u00106R \u0010!\u001a\u0004\u0018\u00010\n8\u0006X\u0087\u0004\u00a2\u0006\u0010\n\u0002\u00107\u0012\u0004\ba\u0010)\u001a\u0004\bb\u00106\u00a8\u0006\u0091\u0001"}, d2 = {"Lcom/dentapp/app/data/model/HealthProfileDto;", "", "seen1", "", "id", "", "patientId", "pregnancyStatus", "trimester", "cardiacCondition", "", "cardiacDetail", "takesBisphosphonates", "bisphosphonateName", "bisphosphonateRoute", "renalInsufficiency", "hepaticInsufficiency", "hemophilia", "pacemaker", "hivStatus", "oncologyActive", "oncologyType", "epilepsy", "eatingDisorder", "gerd", "sjogren", "systemicMeds", "brushingFreq", "ageRange", "tobaccoType", "tobaccoFreq", "dentalAnxiety", "usesFloss", "usesMouthwash", "onboardingComplete", "profileCompleteness", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Integer;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Integer;)V", "getAgeRange$annotations", "()V", "getAgeRange", "()Ljava/lang/String;", "getBisphosphonateName$annotations", "getBisphosphonateName", "getBisphosphonateRoute$annotations", "getBisphosphonateRoute", "getBrushingFreq$annotations", "getBrushingFreq", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCardiacCondition$annotations", "getCardiacCondition", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getCardiacDetail$annotations", "getCardiacDetail", "getDentalAnxiety$annotations", "getDentalAnxiety", "getEatingDisorder$annotations", "getEatingDisorder", "getEpilepsy", "getGerd", "getHemophilia", "getHepaticInsufficiency$annotations", "getHepaticInsufficiency", "getHivStatus$annotations", "getHivStatus", "getId", "getOnboardingComplete$annotations", "getOnboardingComplete", "getOncologyActive$annotations", "getOncologyActive", "getOncologyType$annotations", "getOncologyType", "getPacemaker", "getPatientId$annotations", "getPatientId", "getPregnancyStatus$annotations", "getPregnancyStatus", "getProfileCompleteness$annotations", "getProfileCompleteness", "getRenalInsufficiency$annotations", "getRenalInsufficiency", "getSjogren", "getSystemicMeds$annotations", "getSystemicMeds", "getTakesBisphosphonates$annotations", "getTakesBisphosphonates", "getTobaccoFreq$annotations", "getTobaccoFreq", "getTobaccoType$annotations", "getTobaccoType", "getTrimester", "getUsesFloss$annotations", "getUsesFloss", "getUsesMouthwash$annotations", "getUsesMouthwash", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Integer;)Lcom/dentapp/app/data/model/HealthProfileDto;", "equals", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_debug", "$serializer", "Companion", "app_debug"})
public final class HealthProfileDto {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String patientId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String pregnancyStatus = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer trimester = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean cardiacCondition = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String cardiacDetail = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean takesBisphosphonates = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bisphosphonateName = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bisphosphonateRoute = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean renalInsufficiency = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean hepaticInsufficiency = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean hemophilia = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean pacemaker = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean hivStatus = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean oncologyActive = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String oncologyType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean epilepsy = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean eatingDisorder = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean gerd = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean sjogren = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String systemicMeds = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer brushingFreq = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String ageRange = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String tobaccoType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String tobaccoFreq = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean dentalAnxiety = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean usesFloss = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean usesMouthwash = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean onboardingComplete = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer profileCompleteness = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.dentapp.app.data.model.HealthProfileDto.Companion Companion = null;
    
    public HealthProfileDto(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String patientId, @org.jetbrains.annotations.Nullable()
    java.lang.String pregnancyStatus, @org.jetbrains.annotations.Nullable()
    java.lang.Integer trimester, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean cardiacCondition, @org.jetbrains.annotations.Nullable()
    java.lang.String cardiacDetail, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean takesBisphosphonates, @org.jetbrains.annotations.Nullable()
    java.lang.String bisphosphonateName, @org.jetbrains.annotations.Nullable()
    java.lang.String bisphosphonateRoute, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean renalInsufficiency, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hepaticInsufficiency, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hemophilia, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean pacemaker, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hivStatus, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean oncologyActive, @org.jetbrains.annotations.Nullable()
    java.lang.String oncologyType, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean epilepsy, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean eatingDisorder, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean gerd, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean sjogren, @org.jetbrains.annotations.Nullable()
    java.lang.String systemicMeds, @org.jetbrains.annotations.Nullable()
    java.lang.Integer brushingFreq, @org.jetbrains.annotations.Nullable()
    java.lang.String ageRange, @org.jetbrains.annotations.Nullable()
    java.lang.String tobaccoType, @org.jetbrains.annotations.Nullable()
    java.lang.String tobaccoFreq, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean dentalAnxiety, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean usesFloss, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean usesMouthwash, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean onboardingComplete, @org.jetbrains.annotations.Nullable()
    java.lang.Integer profileCompleteness) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPatientId() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "patient_id")
    @java.lang.Deprecated()
    public static void getPatientId$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPregnancyStatus() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "pregnancy_status")
    @java.lang.Deprecated()
    public static void getPregnancyStatus$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getTrimester() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getCardiacCondition() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "cardiac_condition")
    @java.lang.Deprecated()
    public static void getCardiacCondition$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCardiacDetail() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "cardiac_detail")
    @java.lang.Deprecated()
    public static void getCardiacDetail$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getTakesBisphosphonates() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "takes_bisphosphonates")
    @java.lang.Deprecated()
    public static void getTakesBisphosphonates$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBisphosphonateName() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "bisphosphonate_name")
    @java.lang.Deprecated()
    public static void getBisphosphonateName$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBisphosphonateRoute() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "bisphosphonate_route")
    @java.lang.Deprecated()
    public static void getBisphosphonateRoute$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getRenalInsufficiency() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "renal_insufficiency")
    @java.lang.Deprecated()
    public static void getRenalInsufficiency$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getHepaticInsufficiency() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "hepatic_insufficiency")
    @java.lang.Deprecated()
    public static void getHepaticInsufficiency$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getHemophilia() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getPacemaker() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getHivStatus() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "hiv_status")
    @java.lang.Deprecated()
    public static void getHivStatus$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getOncologyActive() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "oncology_active")
    @java.lang.Deprecated()
    public static void getOncologyActive$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOncologyType() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "oncology_type")
    @java.lang.Deprecated()
    public static void getOncologyType$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getEpilepsy() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getEatingDisorder() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "eating_disorder")
    @java.lang.Deprecated()
    public static void getEatingDisorder$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getGerd() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getSjogren() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSystemicMeds() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "systemic_meds")
    @java.lang.Deprecated()
    public static void getSystemicMeds$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBrushingFreq() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "brushing_freq")
    @java.lang.Deprecated()
    public static void getBrushingFreq$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAgeRange() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "age_range")
    @java.lang.Deprecated()
    public static void getAgeRange$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTobaccoType() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "tobacco_type")
    @java.lang.Deprecated()
    public static void getTobaccoType$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTobaccoFreq() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "tobacco_freq")
    @java.lang.Deprecated()
    public static void getTobaccoFreq$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getDentalAnxiety() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "dental_anxiety")
    @java.lang.Deprecated()
    public static void getDentalAnxiety$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getUsesFloss() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "uses_floss")
    @java.lang.Deprecated()
    public static void getUsesFloss$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getUsesMouthwash() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "uses_mouthwash")
    @java.lang.Deprecated()
    public static void getUsesMouthwash$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getOnboardingComplete() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "onboarding_complete")
    @java.lang.Deprecated()
    public static void getOnboardingComplete$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getProfileCompleteness() {
        return null;
    }
    
    @kotlinx.serialization.SerialName(value = "profile_completeness")
    @java.lang.Deprecated()
    public static void getProfileCompleteness$annotations() {
    }
    
    public HealthProfileDto() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component17() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component19() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component20() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component22() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component25() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component26() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component27() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component28() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component29() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component30() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.dentapp.app.data.model.HealthProfileDto copy(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String patientId, @org.jetbrains.annotations.Nullable()
    java.lang.String pregnancyStatus, @org.jetbrains.annotations.Nullable()
    java.lang.Integer trimester, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean cardiacCondition, @org.jetbrains.annotations.Nullable()
    java.lang.String cardiacDetail, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean takesBisphosphonates, @org.jetbrains.annotations.Nullable()
    java.lang.String bisphosphonateName, @org.jetbrains.annotations.Nullable()
    java.lang.String bisphosphonateRoute, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean renalInsufficiency, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hepaticInsufficiency, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hemophilia, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean pacemaker, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean hivStatus, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean oncologyActive, @org.jetbrains.annotations.Nullable()
    java.lang.String oncologyType, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean epilepsy, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean eatingDisorder, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean gerd, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean sjogren, @org.jetbrains.annotations.Nullable()
    java.lang.String systemicMeds, @org.jetbrains.annotations.Nullable()
    java.lang.Integer brushingFreq, @org.jetbrains.annotations.Nullable()
    java.lang.String ageRange, @org.jetbrains.annotations.Nullable()
    java.lang.String tobaccoType, @org.jetbrains.annotations.Nullable()
    java.lang.String tobaccoFreq, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean dentalAnxiety, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean usesFloss, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean usesMouthwash, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean onboardingComplete, @org.jetbrains.annotations.Nullable()
    java.lang.Integer profileCompleteness) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic()
    public static final void write$Self$app_debug(@org.jetbrains.annotations.NotNull()
    com.dentapp.app.data.model.HealthProfileDto self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/dentapp/app/data/model/HealthProfileDto.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/dentapp/app/data/model/HealthProfileDto;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_debug"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.dentapp.app.data.model.HealthProfileDto> {
        @org.jetbrains.annotations.NotNull()
        public static final com.dentapp.app.data.model.HealthProfileDto.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.dentapp.app.data.model.HealthProfileDto deserialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override()
        public void serialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
        com.dentapp.app.data.model.HealthProfileDto value) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/dentapp/app/data/model/HealthProfileDto$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/dentapp/app/data/model/HealthProfileDto;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.dentapp.app.data.model.HealthProfileDto> serializer() {
            return null;
        }
    }
}