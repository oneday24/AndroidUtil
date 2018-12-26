package com.common.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 * Execute on JVM
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * 1.Tests with no dependency on Android
 * 2.Tests with simple dependency on Android, use Mokito to mock dependency
 */

@RunWith(MockitoJUnitRunner.class)
public class MokitoUnitTest {
    @Mock
    Context mMockContext;
}
