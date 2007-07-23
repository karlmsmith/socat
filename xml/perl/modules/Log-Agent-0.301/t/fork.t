#!perl
###########################################################################
# $Id: fork.t,v 1.2 2002/06/26 18:20:14 sirott Exp $
###########################################################################
#
# fork.t
#
# RCS Revision: $Revision: 1.2 $
# Date: $Date: 2002/06/26 18:20:14 $
#
# Copyright (C) 2002 Mark Rogaski, mrogaski@cpan.org; all rights reserved.
#
# See the README file included with the
# distribution for license information.
#
# $Log: fork.t,v $
# Revision 1.2  2002/06/26 18:20:14  sirott
# 	* Merged v_6_0_base_branch with main branch
#
# Revision 1.1.2.1  2002/06/02 01:11:00  sirott
# 	* Added all required Perl modules to CVS distribution
#
# Revision 1.1  2002/02/23 06:25:20  wendigo
# Initial revision
#
#
###########################################################################

use strict;
use Test;
require 't/common.pl';

BEGIN { plan tests => 19 }

use Log::Agent;
require Log::Agent::Driver::Fork;
require Log::Agent::Driver::Default;
require Log::Agent::Driver::File;
my $driver = Log::Agent::Driver::Fork->make(
    Log::Agent::Driver::Default->make('moose'),
    Log::Agent::Driver::File->make(
        -prefix => 'squirrel',
        -showpid => 1,
	-stampfmt => sub { 'DATE' },
	-channels => {
            'error'  => 't/fork_file.err',
            'output' => 't/fork_file.out'
	},
	-duperr => 1,
    )
);
logconfig( -driver => $driver );

open(ORIGOUT, ">&STDOUT")           or die "can't dup STDOUT: $!\n";
open(STDOUT, ">t/fork_std.out") or die "can't redirect STDOUT: $!\n";
open(ORIGERR, ">&STDERR")           or die "can't dup STDERR: $!\n";
open(STDERR, ">t/fork_std.err") or die "can't redirect STDERR: $!\n";
select(ORIGERR); $| = 1;
select(ORIGOUT); $| = 1;

logerr "out of pez";
logsay "una is a growing pup";
logtrc 'debug', "HLAGHLAGHLAGH";
logwarn "do not try this at home";
eval { logdie "et tu, Chuckles?" };

close STDOUT;
open(STDOUT, ">&ORIGOUT")           or die "can't restore STDOUT: $!\n";
close STDERR;
open(STDERR, ">&ORIGERR")           or die "can't restore STDERR: $!\n";
select(STDOUT);

ok($@);

# default driver output
ok(contains("t/fork_std.err", '^moose: out of pez$'));
ok(! contains("t/fork_std.err", '^Out of pez$'));
ok(contains("t/fork_std.err", '^moose: una is a growing pup$'));
ok(! contains("t/fork_std.err", '^Una is a growing pup$'));
ok(contains("t/fork_std.err", '^moose: et tu, Chuckles\?$'));
ok(! contains("t/fork_std.err", '^Et tu, Chuckles\?$'));
ok(contains("t/fork_std.err", '^moose: do not try this at home$'));
ok(! contains("t/fork_std.err", '^Do not try this at home$'));
ok(! contains("t/fork_std.err", '^moose: HLAGHLAGHLAGH$'));
ok(-s "t/fork_std.out", 0);

# file driver output
ok(contains("t/fork_file.err", '^DATE squirrel\[\d+\]: out of pez$'));
ok(contains("t/fork_file.out", 'ERROR: out of pez'));
ok(contains("t/fork_file.out", '^DATE squirrel\[\d+\]: una is a growing pup$'));
ok(! contains("t/fork_file.err", 'una is a growing pup'));
ok(contains("t/fork_file.err", '^DATE squirrel\[\d+\]: do not try this at home$'));
ok(contains("t/fork_file.out", 'WARNING: do not try this at home'));
ok(contains("t/fork_file.err", '^DATE squirrel\[\d+\]: et tu, Chuckles\?$'));
ok(contains("t/fork_file.out", 'FATAL: et tu, Chuckles\?'));

unlink 't/fork_std.out', 't/fork_std.err',
        't/fork_file.out', 't/fork_file.err';


