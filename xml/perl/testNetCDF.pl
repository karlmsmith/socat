#!/usr/bin/perl -w
# Copyright (c) 2001 TMAP, NOAA
# ALL RIGHTS RESERVED
#
# Please read the full copyright notice in the file COPYRIGHT
# included with this code distribution

use LAS;
require "LASNetCDF.pm";

package main;
use strict;

sub dumpCDF {
    my $cdf = shift;
    my %globalAtts = $cdf->getAttributes;
    foreach (keys %globalAtts){
	print "Global attribute: $_ = '$globalAtts{$_}'\n";
    }

    my %vars = $cdf->getVariables;
    foreach (keys %vars){
	my $var = $vars{$_};
	print "Variable: $_ Type: ", $var->getTypeString, "\n";
	my %atts = $var->getAttributes;
	foreach (keys %atts){
	    print "\tAttribute: $_ = '$atts{$_}'\n";
	}
	my @dims = $var->getDims;
	foreach (@dims){
	    print "\tDim: name: ", $_->getName, " size: ", $_->getSize, "\n";
	    my @data = $_->getData;
	    if (@data){
		print "\t\tData: ";
		foreach (@data) {
		    print "$_ ";
		}
		print "\n";
	    }
	}
    }
}

package main;

sub prettyPrint {
    $_ = shift;
    my $indent = -1;
    s/\n//g;
    my @lines = split(/\</);
    foreach (@lines){
	my $flag = 0;
  	if (/^\//) {
	    $indent--;
	    $flag = 1;
	}
  	println "  " x $indent,"<",$_ if $_;
  	$indent++ if (! /\/>/ && ! $flag);
    }
}

sub doit {
    my $parser = new LAS::Parser("template.xml");
    my $config = new LAS::Config($parser);
    
    my $fname = $ARGV[0];
    if (!$fname){
	$fname = "/home/fe1/dsets/data/ocean_atlas_monthly.cdf";
    }
    my $cdf = new LAS::NetCDF($fname);
#dumpCDF $cdf;
    new LAS::NetCDF::XML($cdf, $config, $parser);
    prettyPrint $config->toXML;
}
    
sub testDates {
    my @testHours = (366, 1096.485, 1826.97, 2557.455,
		     3287.94, 4018.425, 4748.91,
		     5479.395, 6209.88, 6940.365, 7670.85, 8401.335);
    my @testSecs = (86400, 129600);
    my @testMins = (1440, 2160);
    my @testDays = (3,5.5,7,9);
    my @testYears = (2, 100);
    my @testMonths = (3, 12);
    println "Hours:";
    my $date =
	new LAS::NetCDF::DateConvert("hour since 1999-01-01 00:00:00");
    foreach (@testHours){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
    println "Seconds:";
    $date =
	new LAS::NetCDF::DateConvert("seconds since 1999-01-01 12:01:02");
    foreach (@testSecs){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
    println "Minutes:";
    $date =
	new LAS::NetCDF::DateConvert("minutes since 1999-01-01 12:01:02");
    foreach (@testMins){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
    println "Days:";
    $date =
	new LAS::NetCDF::DateConvert("days since 1999-01-01 12:01:02");
    foreach (@testDays){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
    println "Months:";
    $date =
	new LAS::NetCDF::DateConvert("months since 1999-01-01 12:01:02");
    foreach (@testMonths){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
    println "Years:";
    $date =
	new LAS::NetCDF::DateConvert("years since 1999-01-01 12:01:02");
    foreach (@testYears){
	my ($y,$mo,$d,$h,$m,$s) = $date->translate($_);
	println "$y-$mo-$d $h:$m:$s";
    }
}

doit;
#testDates;

